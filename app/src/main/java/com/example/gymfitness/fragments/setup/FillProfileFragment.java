package com.example.gymfitness.fragments.setup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.activities.HomeActivity;
import com.example.gymfitness.data.DAO.UserInformationDAO;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.databinding.FragmentFillProfileBinding;
import com.example.gymfitness.helpers.ValidationHelpers;
import com.example.gymfitness.viewmodels.SetUpViewModel;
import com.example.gymfitness.viewmodelsfactory.SetUpViewModelFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FillProfileFragment extends Fragment {

    private FragmentFillProfileBinding binding;
    private SetUpViewModel setUpViewModel;
    private String fullname, nickname, email, phonenumber;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private ExecutorService executorService;

    public FillProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fill_profile, container, false);

        setUpViewModel = new ViewModelProvider(requireActivity()).get(SetUpViewModel.class);

        sharedPreferences = getActivity().getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // thread
        executorService = Executors.newCachedThreadPool();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFillProfile();
                boolean validateRS = validateFields();
                if(validateRS)
                {
                    setData();
                    executorService.execute(() -> {
                        setUpViewModel.saveUserInformation();
                    });
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }

    private boolean validateFields()
    {
        boolean fullnameVLD = ValidationHelpers.validateEmpty(fullname,binding.edtFullName,"Fullname cannot be empty");
        boolean nicknameVLD = ValidationHelpers.validateEmpty(nickname,binding.edtNickname,"Nickname cannot be empty");
        boolean emailVLD = ValidationHelpers.validateEmail(email,binding.edtEmail);
        boolean phoneNBVLD = ValidationHelpers.validatePhoneNumber(phonenumber,binding.edtPhoneNumber);
        if (!fullnameVLD || !nicknameVLD || !emailVLD || !phoneNBVLD)
            return false;
        return true;
    }

    private void setData()
    {
        setUpViewModel.setFullname(fullname.trim());
        setUpViewModel.setNickname(nickname.trim());
        setUpViewModel.setEmail(email.trim());
        setUpViewModel.setPhonenumber(phonenumber.trim());
        editor.putInt("done",1);
        editor.apply();
    }

    private void setFillProfile()
    {
        fullname = binding.edtFullName.getText().toString();
        nickname = binding.edtNickname.getText().toString();
        email = binding.edtEmail.getText().toString();
        phonenumber = binding.edtPhoneNumber.getText().toString();
    }
}