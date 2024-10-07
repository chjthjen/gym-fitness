package com.example.gymfitness.viewmodels.viewmodelsfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymfitness.data.DAO.UserInformationDAO;
import com.example.gymfitness.viewmodels.SetUpViewModel;

public class SetUpViewModelFactory implements ViewModelProvider.Factory {
    private final UserInformationDAO userInformationDAO;

    public SetUpViewModelFactory(UserInformationDAO userInformationDAO) {
        this.userInformationDAO = userInformationDAO;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SetUpViewModel.class)) {
            return (T) new SetUpViewModel(userInformationDAO);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
