package com.example.gymfitness.retrofit;

import com.example.gymfitness.data.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GymApi {

    @GET("your_endpoint")
    Call<String> getData() ;

    @FormUrlEncoded
    @POST("/api/signup")
    Call<ApiResponse> signup(
            @Field("user_email") String userEmail,
            @Field("user_fullname") String userFullName,
            @Field("user_id") String userId
    );
}
