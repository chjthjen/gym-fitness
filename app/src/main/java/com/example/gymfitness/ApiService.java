package com.example.gymfitness;

import com.example.gymfitness.data.UserAccount;
import com.example.gymfitness.data.UserInfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/saveuseraccount")
    Call<ResponseBody> saveUserAccount(@Body UserAccount userAccount);

    @POST("api/saveuserinfo")
    Call<ResponseBody> saveUserInfo(@Body UserInfo userInfo);
}