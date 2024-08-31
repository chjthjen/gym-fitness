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
        Call<ApiResponse> call = gymApi.checkExist(email);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful() && response.body() != null)
                {
                    String message = response.body().getMessage();
                    if ("Email already exists".equals(message)) {
                        emailExists.setValue(true);
                    } else {
                        emailExists.setValue(false);
                        errorMessage.setValue("Can't find account");
                    }
                }
                else
                    errorMessage.setValue("Error " + response.message());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                errorMessage.setValue("Error: " + t.getMessage());
            }
        });

    }
}