package com.example.gymfitness.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.SignUpCallback;
import com.example.gymfitness.data.UserSignUp;
import com.example.gymfitness.data.Users;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private MutableLiveData<UserSignUp> user = new MutableLiveData<>();
    private GoogleSignInClient googleSignInClient;
    private CallbackManager callbackManager;

    public static final int RC_SIGN_IN = 40;

    public SignUpViewModel() {
        mAuth = FirebaseAuth.getInstance();
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

    public boolean checkUserStatus() {
        return mAuth.getCurrentUser() != null;
    }
}

