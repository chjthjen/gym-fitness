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
    public LiveData<Boolean> getIsLoginSuccessful() {
        return isLoginSuccessful;
    }


    //signup
    public boolean checkUserStatus(){
        FirebaseUser currentUser=mAuth.getCurrentUser();
        return currentUser != null;
    }

    public void signUp(){
        mAuth.createUserWithEmailAndPassword(user.getValue().getUser_email(),user.getValue().getUser_password())
                .addOnCompleteListener(task -> {
                    if(task.isComplete()){
                        Log.d("khanh", "createUserWithEmail:success");
                    }
                    else{
                        Log.w("khanh", "createUserWithEmail:failure", task.getException());
                    }
                });
    }


    public void setUser(UserAccount userSignUp) {
        this.user.setValue(userSignUp);
    }

}

