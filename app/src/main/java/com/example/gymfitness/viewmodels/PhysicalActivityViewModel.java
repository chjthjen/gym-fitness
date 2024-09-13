package com.example.gymfitness.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.graphics.Color;

public class PhysicalActivityViewModel extends ViewModel {

    private final MutableLiveData<Integer> beginnerButtonColor = new MutableLiveData<>(Color.parseColor("#32CD32")); // limegreen
    private final MutableLiveData<Integer> intermediateButtonColor = new MutableLiveData<>(Color.WHITE);
    private final MutableLiveData<Integer> advancedButtonColor = new MutableLiveData<>(Color.WHITE);

    public void onBeginnerClicked() {
        beginnerButtonColor.setValue(Color.parseColor("#32CD32"));
        intermediateButtonColor.setValue(Color.WHITE);
        advancedButtonColor.setValue(Color.WHITE);
    }

    public void onIntermediateClicked() {
        beginnerButtonColor.setValue(Color.WHITE);
        intermediateButtonColor.setValue(Color.parseColor("#32CD32"));
        advancedButtonColor.setValue(Color.WHITE);
    }

    public void onAdvancedClicked() {
        beginnerButtonColor.setValue(Color.WHITE);
        intermediateButtonColor.setValue(Color.WHITE);
        advancedButtonColor.setValue(Color.parseColor("#32CD32"));
    }

    public LiveData<Integer> getBeginnerButtonColor() {
        return beginnerButtonColor;
    }

    public LiveData<Integer> getIntermediateButtonColor() {
        return intermediateButtonColor;
    }

    public LiveData<Integer> getAdvancedButtonColor() {
        return advancedButtonColor;
    }
}
