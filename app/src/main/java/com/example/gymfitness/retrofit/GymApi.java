package com.example.gymfitness.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GymApi {

    @GET("your_endpoint")
    Call<String> getData() ;
}
