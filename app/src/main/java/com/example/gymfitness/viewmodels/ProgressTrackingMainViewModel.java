package com.example.gymfitness.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.DAO.UserInformationDAO;
import com.example.gymfitness.data.entities.UserInformation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProgressTrackingMainViewModel extends ViewModel {
    private final MutableLiveData<UserInformation> userInformation = new MutableLiveData<>();
    private final UserInformationDAO userInformationDAO;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ProgressTrackingMainViewModel(UserInformationDAO userInformationDAO) {
        this.userInformationDAO = userInformationDAO;
    }

    public void loadUserInformation() {
        executorService.execute(() -> {
            UserInformation info = userInformationDAO.getUserInformation();
            userInformation.postValue(info);
        });
    }

    public LiveData<UserInformation> getUserInformation() {
        return userInformation;
    }
}
