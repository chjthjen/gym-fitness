package com.example.gymfitness.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.db.UserAccount;
import com.google.firebase.auth.FirebaseAuth;

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

