package com.example.gymfitness.fragments.floatingMenu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.UserInformation;
import com.example.gymfitness.databinding.FragmentEditprofileBinding;
import com.example.gymfitness.viewmodels.ProfileViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class EditProfileFragment extends Fragment {

    FragmentEditprofileBinding binding;
    private ProfileViewModel profileViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_editprofile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileViewModel.getUserInformation().observe(this, new Observer<UserInformation>() {
            @Override
            public void onChanged(UserInformation userInfo) {
                if (userInfo != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String dateOfBirth= sdf.format(userInfo.getDob());

                    binding.tvNameProfile.setText(userInfo.getFullname());
                    binding.tvEmail.setText(userInfo.getEmail());
                    binding.tvDateOfBirth.setText(dateOfBirth);
                    binding.tvWeight.setText(String.valueOf(userInfo.getWeight()));
                    binding.tvOld.setText(String.valueOf(userInfo.getAge()));
                    binding.tvHeigh.setText(String.valueOf(userInfo.getHeight()));
                    binding.editFullname.setText(userInfo.getFullname());
                    binding.edtEmail.setText(userInfo.getEmail());
                    binding.edtPhoneNumber.setText(userInfo.getPhonenumber());
                    binding.edtDateOfBirth.setText(dateOfBirth);
                    binding.edtWeight.setText(String.valueOf(userInfo.getWeight()));
                    binding.edtHeigh.setText(String.valueOf(userInfo.getHeight()));
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}