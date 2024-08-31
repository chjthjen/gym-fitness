package com.example.gymfitness.retrofit;

import com.example.gymfitness.data.ApiResponse;
import com.example.gymfitness.data.UserAccount;
import com.example.gymfitness.data.UserInfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
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

    @POST("api/signup")
    Call<ResponseBody> saveUserAccount(@Body UserAccount userAccount);

    @POST("api/saveuserinfo")
    Call<ResponseBody> saveUserInfo(@Body UserInfo userInfo);

    @FormUrlEncoded
    @POST("api/checkExist")
    Call<ApiResponse> checkExist(@Field("user_email") String userEmail);


}
