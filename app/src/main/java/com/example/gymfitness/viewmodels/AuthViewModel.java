package com.example.gymfitness.viewmodels;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.gymfitness.R;
import com.example.gymfitness.fragments.authentication.SignUpFragment;

public class AuthViewModel extends ViewModel {
    public MutableLiveData<String> email;
    public MutableLiveData<String> password;
    private MutableLiveData<Boolean> isLoginSuccessful;
    public MutableLiveData<String> fragmentName = new MutableLiveData<>();

    public AuthViewModel() {
        email = new MutableLiveData<>();
        password = new MutableLiveData<>();
        isLoginSuccessful = new MutableLiveData<>();

    }
    public LiveData<Boolean> getIsLoginSuccessful() {
        return isLoginSuccessful;
    }

}

