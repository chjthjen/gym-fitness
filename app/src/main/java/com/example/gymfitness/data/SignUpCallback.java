package com.example.gymfitness.data;

public interface SignUpCallback {
    void onSuccess();
    void onFailure(int errorCode);
}
