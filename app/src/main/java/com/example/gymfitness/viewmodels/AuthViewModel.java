package com.example.gymfitness.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.UserAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends ViewModel {
    public MutableLiveData<String> email;
    public MutableLiveData<String> password;
    private MutableLiveData<Boolean> isLoginSuccessful;
    public MutableLiveData<String> fragmentName = new MutableLiveData<>();

    //signup
    private final FirebaseAuth mAuth;
    private MutableLiveData<UserAccount> user;

    public AuthViewModel() {
        email = new MutableLiveData<>();
        password = new MutableLiveData<>();
        isLoginSuccessful = new MutableLiveData<>();

        this.mAuth = FirebaseAuth.getInstance();
        this.user = new MutableLiveData<>();

    }

}

