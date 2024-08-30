package com.example.gymfitness.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OnBoardViewmodel extends ViewModel {
    private final MutableLiveData<Integer> currentItem = new MutableLiveData<>(0);

    public LiveData<Integer> getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(int position) {
        currentItem.setValue(position);
    }
}
