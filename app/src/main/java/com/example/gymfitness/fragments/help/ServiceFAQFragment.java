package com.example.gymfitness.fragments.help;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.FAQAdapter;
import com.example.gymfitness.databinding.FragmentAccountFAQBinding;
import com.example.gymfitness.databinding.FragmentGeneralFAQBinding;
import com.example.gymfitness.databinding.FragmentServiceFAQBinding;

import java.util.Arrays;
import java.util.List;

public class ServiceFAQFragment extends Fragment {
    private FragmentServiceFAQBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_f_a_q, container, false);
        binding = FragmentServiceFAQBinding.bind(view);
        return binding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();
        FAQAdapter adapter;

        List<String> titles = Arrays.asList(
                getString(R.string.lorem_ipsum_dolor_sit_amet),
                getString(R.string.lorem_ipsum_dolor_sit_amet),
                getString(R.string.lorem_ipsum_dolor_sit_amet));
        List<String> contents = Arrays.asList(
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_n_sed_do_eiusmod_tempor_incididunt_ut_labore_et_dolore_n_magna_aliqua),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_n_sed_do_eiusmod_tempor_incididunt_ut_labore_et_dolore_n_magna_aliqua),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_n_sed_do_eiusmod_tempor_incididunt_ut_labore_et_dolore_n_magna_aliqua)
        );

        adapter = new FAQAdapter(getActivity(), titles, contents);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.reVService.setLayoutManager(linearLayoutManager);
        binding.reVService.setAdapter(adapter);
    }
}