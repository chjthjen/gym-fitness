package com.example.gymfitness.viewmodels.viewmodelsfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymfitness.data.DAO.UserInformationDAO;
import com.example.gymfitness.viewmodels.EditProfileViewModel;

public class EditProfileViewModelFactory implements ViewModelProvider.Factory {
    private final UserInformationDAO userInformationDAO;

    public EditProfileViewModelFactory(UserInformationDAO userInformationDAO) {
        this.userInformationDAO = userInformationDAO;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EditProfileViewModel.class)) {
            return (T) new EditProfileViewModel(userInformationDAO);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
