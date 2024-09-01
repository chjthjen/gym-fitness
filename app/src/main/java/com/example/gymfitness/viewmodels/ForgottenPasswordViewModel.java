package com.example.gymfitness.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.ApiResponse;
import com.example.gymfitness.data.UserAccount;
import com.example.gymfitness.retrofit.GymApi;
import com.example.gymfitness.retrofit.RetrofitInstance;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgottenPasswordViewModel extends ViewModel {

    private final GymApi gymApi;
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> emailExists = new MutableLiveData<>();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public ForgottenPasswordViewModel() {
        gymApi = RetrofitInstance.getApiService();
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getEmailExists() {
        return emailExists;
    }

    public void checkEmail(String email) {
        Call<ApiResponse> call = gymApi.checkExist(email);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 200) {
                    emailExists.setValue(true);
                } else if (response.code() == 400) {
                    emailExists.setValue(false);
                    errorMessage.setValue("Can't find account");
                } else
                    errorMessage.setValue("Error " + response.message());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                errorMessage.setValue("Error: " + t.getMessage());
            }
        });
    }

    ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl("https://firebaselearnbqd.page.link/resetpassword")
            .setHandleCodeInApp(true)
            .setAndroidPackageName(
                    "com.example.gymfitness",
                    false,
                    null )
            .build();

    public void sendResetPasswordLink(String email) {
        auth.sendPasswordResetEmail(email, actionCodeSettings)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        errorMessage.setValue("Reset password email sent.");
                    } else {
                        errorMessage.setValue("Error: " + task.getException().getMessage());
                    }
                });
    }
}