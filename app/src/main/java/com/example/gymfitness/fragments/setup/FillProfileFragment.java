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
import com.example.gymfitness.databinding.FragmentFillProfileBinding;
import com.example.gymfitness.viewmodels.SetUpViewModel;

public class FillProfileFragment extends Fragment {

    private FragmentFillProfileBinding binding;
    private SetUpViewModel setUpViewModel;
    private String fullname, nickname, email, phonenumber;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFillProfile();
                saveToPref();
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void saveToPref()
    {
        editor.putString("fullname",fullname);
        editor.putString("nickname",nickname);
        editor.putString("email",email);
        editor.putString("phonenumber",phonenumber);
        editor.apply();
    }

    private void setFillProfile()
    {
        fullname = binding.edtFullName.getText().toString();
        nickname = binding.edtNickname.getText().toString();
        email = binding.edtEmail.getText().toString();
        phonenumber = binding.edtMobieNumber.getText().toString();
    }
}