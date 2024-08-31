package com.example.gymfitness.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.ApiResponse;
import com.example.gymfitness.data.UserAccount;
import com.example.gymfitness.retrofit.GymApi;
import com.example.gymfitness.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgottenPasswordViewModel extends ViewModel {

    private final GymApi gymApi;
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> emailExists = new MutableLiveData<>();

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
        gymApi.checkExist(email).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getMessage().equals("Email already exists")) {
                        emailExists.setValue(true);
                    } else {
                        emailExists.setValue(false);
                        errorMessage.setValue("Email not found");
                    }
                } else {
                    errorMessage.setValue("Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                errorMessage.setValue("API call failed: " + t.getMessage());
            }
        });
    }
}