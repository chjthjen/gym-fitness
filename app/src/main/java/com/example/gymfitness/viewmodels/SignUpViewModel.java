package com.example.gymfitness.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.ApiResponse;
import com.example.gymfitness.data.SignUpCallback;
import com.example.gymfitness.data.UserSignUp;
import com.example.gymfitness.data.Users;
import com.example.gymfitness.retrofit.GymApi;
import com.example.gymfitness.retrofit.RetrofitInstance;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private MutableLiveData<UserSignUp> user;
    private GoogleSignInClient googleSignInClient;
    private CallbackManager callbackManager;

    public static final int RC_SIGN_IN = 40;
    private final GymApi gymApi= RetrofitInstance.getApiService();
    public SignUpViewModel() {
        mAuth = FirebaseAuth.getInstance();
        user=  new MutableLiveData<>();
        database = FirebaseDatabase.getInstance();
    }

    public GoogleSignInClient getGoogleSignInClient(Context context, String webClientId) {
        if (googleSignInClient == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(webClientId)
                    .requestEmail()
                    .build();
            googleSignInClient = GoogleSignIn.getClient(context, gso);
        }
        return googleSignInClient;
    }

    public CallbackManager getCallbackManager() {
        if (callbackManager == null) {
            callbackManager = CallbackManager.Factory.create();
        }
        return callbackManager;
    }

    public void signUp(SignUpCallback signUpCallback) {
        UserSignUp userSignUp = user.getValue();
        if (userSignUp == null) return;

        mAuth.createUserWithEmailAndPassword(userSignUp.getEmail(), userSignUp.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        signUpCallback.onSuccess();
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            signUpCallback.onFailure(-999); // Email already used
                        } else {
                            signUpCallback.onFailure(-1000); // Generic failure
                        }
                    }
                });
    }

    public void signInWithGoogle(Intent data, GoogleSignInClient googleSignInClient, Activity activity, SignUpCallback signUpCallback) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        task.addOnCompleteListener(activity, googleSignInTask -> {
            if (googleSignInTask.isSuccessful()) {
                try {
                    GoogleSignInAccount account = googleSignInTask.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account.getIdToken(), signUpCallback);
                } catch (ApiException e) {
                    signUpCallback.onFailure(-1000);
                }
            } else {
                signUpCallback.onFailure(-1000);
            }
        });
    }

    private void firebaseAuthWithGoogle(String idToken, SignUpCallback signUpCallback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveUserToDatabase(mAuth.getCurrentUser());
                        signUpCallback.onSuccess();
                    } else {
                        signUpCallback.onFailure(-1000);
                    }
                });
    }

    public void signInWithFacebook(AccessToken token, SignUpCallback signUpCallback) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveUserToDatabase(mAuth.getCurrentUser());
                        signUpCallback.onSuccess();
                    } else {
                        signUpCallback.onFailure(-1000);
                    }
                });
    }

    private void saveUserToDatabase(FirebaseUser user) {
        if (user != null) {
            Users users = new Users();
            users.setUserId(user.getUid());
            users.setName(user.getDisplayName());
            users.setProfile(user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "");


            database.getReference().child("Users").child(user.getUid()).setValue(users);
        }
    }


    public void setUser(UserSignUp userSignUp) {
        this.user.setValue(userSignUp);
    }

    public void saveDB(SignUpCallback signUpCallback){
        Call<ApiResponse> call=gymApi.signup(user.getValue().getEmail(),user.getValue().getFullName(), mAuth.getUid());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                signUpCallback.onSuccess();
                mAuth.signOut();
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                signUpCallback.onFailure(-1000);
            }
        });
    }

    public boolean checkUserStatus() {
        return mAuth.getCurrentUser() != null;
    }

    public void deleteUserFromFirebase(SignUpCallback signUpCallback){
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser!=null){
            AuthCredential credential= EmailAuthProvider
                    .getCredential(user.getValue().getEmail(),user.getValue().getPassword());

            firebaseUser.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            firebaseUser.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                signUpCallback.onSuccess();
                                                mAuth.signOut();
                                            }
                                            else{
                                                signUpCallback.onFailure(-1000);
                                                mAuth.signOut();
                                            }
                                        }
                                    });
                        }
                    });
        }

    }
}

