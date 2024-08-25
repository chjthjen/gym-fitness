package com.example.gymfitness.fragments.authentication;

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
import com.example.gymfitness.activities.AuthenticateActivity;
import com.example.gymfitness.databinding.FragmentLoginBinding;
import com.example.gymfitness.databinding.FragmentSignUpBinding;
import com.example.gymfitness.viewmodels.AuthViewModel;

public class SignUpFragment extends Fragment {
    private AuthViewModel viewModel;
    FragmentSignUpBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
    }

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(AuthViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

//    private void navigateToLogin() {
//        getParentFragmentManager().beginTransaction()
//                .replace(R.id.frame_layout, new LoginFragment())
//                .commit();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        AuthViewModel viewModel = new ViewModelProvider(getActivity()).get(AuthViewModel.class);
//        viewModel.fragmentName.setValue("Create Account");
//    }
}