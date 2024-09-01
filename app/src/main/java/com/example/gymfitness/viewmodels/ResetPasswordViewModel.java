package com.example.gymfitness.viewmodels;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordViewModel extends ViewModel {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private final MutableLiveData<String> passwordResetStatus = new MutableLiveData<>();
    private final MutableLiveData<Boolean> passwordResetSuccess = new MutableLiveData<>(false);

    public LiveData<String> getPasswordResetStatus() {
        return passwordResetStatus;
    }
    public LiveData<Boolean> getPasswordResetSuccess() {
        return passwordResetSuccess;
    }
    public void resetPassword(String oobCode, String password) {
        if (oobCode != null) {
            auth.confirmPasswordReset(oobCode, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            passwordResetStatus.setValue("Mật khẩu đã được đặt lại");
                            passwordResetSuccess.setValue(true);

                        } else {
                            passwordResetStatus.setValue("Không thể đặt lại mật khẩu: " + task.getException().getMessage());
                        }
                    });
        } else {
            passwordResetStatus.setValue("Mã xác thực không hợp lệ");
        }
    }
}
