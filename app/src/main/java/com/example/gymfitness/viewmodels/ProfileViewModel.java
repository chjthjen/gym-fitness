package com.example.gymfitness.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.gymfitness.data.entities.UserInformation;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<UserInformation> userInformationLiveData = new MutableLiveData<>();

    public LiveData<UserInformation> getUserInformation() {
        return userInformationLiveData;
    }

    public void setUserInformation(UserInformation userInformation) {
        userInformationLiveData.setValue(userInformation);
    }

    public void setImagePath(String imagePath) {
        UserInformation userInformation = userInformationLiveData.getValue();
        if (userInformation != null) {
            userInformation.setImagePath(imagePath);
            userInformationLiveData.setValue(userInformation);
        }
    }
}