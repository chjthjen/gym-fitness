package com.example.gymfitness.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.stream.Collectors;

public class LogoutDialogViewModel extends ViewModel {

    private FirebaseUser user;
    private MutableLiveData<Boolean> isLogout = new MutableLiveData<>();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private GoogleSignInClient mGoogleSignInClient;
    public void setGoogleSignInClient(GoogleSignInClient googleSignInClient) {
        this.mGoogleSignInClient = googleSignInClient;
    }
    public LogoutDialogViewModel() {
    }

    public LiveData<Boolean> getIsLogout() {
        return isLogout;
    }

    public void logout() {
        user = firebaseAuth.getCurrentUser();
        // check sign in method and sign out
        if (user != null) {
            List<String> providerData = user.getProviderData()
                    .stream()
                    .map(userInfo -> userInfo.getProviderId())
                    .collect(Collectors.toList());
            if (providerData.contains("google.com")) {
                mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
                    firebaseAuth.signOut();
                    isLogout.postValue(true);
                });
            } else if (providerData.contains("facebook.com")) {
                firebaseAuth.signOut();
                isLogout.postValue(true);
            } else if (providerData.contains("password")) {
                firebaseAuth.signOut();
                isLogout.postValue(true);
            } else {
                firebaseAuth.signOut();
                isLogout.postValue(true);
            }
        } else {
            Log.e("Logout", "No user is currently signed in");
            isLogout.postValue(false);
        }
    }
}
