package com.example.gymfitness.viewmodels;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.SignUpCallback;
import com.example.gymfitness.data.UserSignUp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpViewModel extends ViewModel {


    private FirebaseAuth mAuth;
    private MutableLiveData<UserSignUp> user;
    public SignUpViewModel() {
        this.mAuth = FirebaseAuth.getInstance();
        this.user=new MutableLiveData<>();
    }
    public boolean checkUserStatus(){
        FirebaseUser currentUser=mAuth.getCurrentUser();
        return currentUser != null;
    }


    // code -1 là gmail la địa chỉ email đang được tài khoản khác sử dụng
    public void signUp(SignUpCallback signUpCallback) {
        mAuth.createUserWithEmailAndPassword(user.getValue().getEmail(), user.getValue().getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        signUpCallback.onSuccess();
                        mAuth=null;
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            signUpCallback.onFailure(-999);
                            Log.d("khanh",task.getException().toString());
                        }
                        else{
                            signUpCallback.onFailure(-1000);
                        }
                    }
                });
    }


    public void setUser(UserSignUp userSignUp) {
        this.user.setValue(userSignUp);
    }
}
