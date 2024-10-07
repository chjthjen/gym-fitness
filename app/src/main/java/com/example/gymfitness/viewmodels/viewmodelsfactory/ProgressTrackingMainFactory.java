package com.example.gymfitness.viewmodels.viewmodelsfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymfitness.data.DAO.UserInformationDAO;
import com.example.gymfitness.viewmodels.EditProfileViewModel;
import com.example.gymfitness.viewmodels.ProgressTrackingMainViewModel;

public class ProgressTrackingMainFactory implements ViewModelProvider.Factory {
    private final UserInformationDAO userInformationDAO;

    public ProgressTrackingMainFactory(UserInformationDAO userInformationDAO) {
        this.userInformationDAO = userInformationDAO;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProgressTrackingMainViewModel.class)) {
            return (T) new ProgressTrackingMainViewModel(userInformationDAO);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
