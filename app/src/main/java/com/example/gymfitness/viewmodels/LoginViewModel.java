package com.example.gymfitness.viewmodels;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.gymfitness.R;
import com.example.gymfitness.utils.Resource;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginViewModel extends AndroidViewModel {

    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;
    private MutableLiveData<Resource<FirebaseUser>> currentUser = new MutableLiveData<>();
    private MutableLiveData<Resource<String>> errorMessage = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(application.getString(R.string.default_web_client_id)) // id trên firebase
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(application, gso);
    }

    public void loginWithEmail(String usernameOrEmail, String password) {
        currentUser.setValue(new Resource.Loading<>()); // Set loading state
        auth.signInWithEmailAndPassword(usernameOrEmail, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        currentUser.setValue(new Resource.Success<>(auth.getCurrentUser()));
                    } else {
                        errorMessage.setValue(new Resource.Error<>("Login failed: " + task.getException().getMessage()));
                    }
                });
    }

    public void loginWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();

    }

    public void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            errorMessage.setValue(new Resource.Error<>("Google Sign-In failed: " + e.getMessage()));
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        currentUser.setValue(new Resource.Success<>(auth.getCurrentUser()));
                    } else {
                        errorMessage.setValue(new Resource.Error<>("Authentication failed."));
                    }
                });
    }

    public LiveData<Resource<FirebaseUser>> getCurrentUser() {
        return currentUser;
    }

    public LiveData<Resource<String>> getErrorMessage() {
        return errorMessage;
    }

    public void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        currentUser.setValue(new Resource.Success<>(auth.getCurrentUser()));
                    } else {
                        errorMessage.setValue(new Resource.Error<>("Authentication failed."));
                    }
                });
    }
}