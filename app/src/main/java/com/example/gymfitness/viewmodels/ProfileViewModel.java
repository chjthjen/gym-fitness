package com.example.gymfitness.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.DAO.UserInformationDAO;
import com.example.gymfitness.data.entities.UserInformation;

public class ProfileViewModel extends ViewModel {

    private UserInformationDAO userInformationDAO;
    private MutableLiveData<UserInformation> userInformation;

    public ProfileViewModel(UserInformationDAO userInformationDAO){
        this.userInformationDAO = userInformationDAO;
        userInformation = new MutableLiveData<>();
    }

    private void loadUserInfo(){
        UserInformation information = userInformationDAO.getUserInformation();
        userInformation.setValue(information);
    }

    public LiveData<UserInformation> getUserInformation() {
        return userInformation;
    }
}
