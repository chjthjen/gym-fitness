package com.example.gymfitness.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.entities.Workout;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Workout> selected = new MutableLiveData<Workout>();

    public void select(Workout item) {
        selected.setValue(item);
    }

    public LiveData<Workout> getSelected() {
        return selected;
    }
}
