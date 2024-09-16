package com.example.gymfitness.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gymfitness.data.DAO.UserInformationDAO;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.UserInformation;
import com.example.gymfitness.utils.UserData;
import com.facebook.internal.qualityvalidation.Excuse;

import org.jetbrains.annotations.Async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EditProfileViewModel extends ViewModel {
    private final MutableLiveData<UserInformation> userInformation = new MutableLiveData<>();
    private final UserInformationDAO userInformationDAO;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public EditProfileViewModel(UserInformationDAO userInformationDAO) {
        this.userInformationDAO = userInformationDAO;
    }

    public void loadUserInformation() {
        executorService.execute(() -> {
            UserInformation info = userInformationDAO.getUserInformation();
            userInformation.postValue(info);
        });
    }

    public void updateUserInformation(UserInformation info) {
        executorService.execute(() -> {
            userInformationDAO.upsertWorkout(info);
            loadUserInformation();
        });
    }

    public LiveData<UserInformation> getUserInformation() {
        return userInformation;
    }
}
