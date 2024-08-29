package com.example.gymfitness.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class  RetrofitInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://localhost"; // link url cua server
      //tao rieng
    private RetrofitInstance() {

    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            synchronized (RetrofitInstance.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static GymApi getApiService() {
        return getRetrofitInstance().create(GymApi.class);
    }
}
