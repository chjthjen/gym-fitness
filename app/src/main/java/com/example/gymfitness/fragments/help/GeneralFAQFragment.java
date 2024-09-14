package com.example.gymfitness.fragments.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;
import com.example.gymfitness.R;
import com.example.gymfitness.adapters.FAQAdapter;
import com.example.gymfitness.adapters.help.FAQViewpagerAdapter;
import com.example.gymfitness.databinding.FragmentFaqBinding;
import com.example.gymfitness.databinding.FragmentGeneralFAQBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class GeneralFAQFragment extends Fragment {

    private FragmentGeneralFAQBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general_f_a_q, container, false);
        binding = FragmentGeneralFAQBinding.bind(view);
        return binding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();
        FAQAdapter adapter;

        List<String> titles = Arrays.asList(
                getString(R.string.lorem_ipsum_dolor_sit_amet),
                getString(R.string.lorem_ipsum_dolor_sit_amet),
                getString(R.string.lorem_ipsum_dolor_sit_amet),
                getString(R.string.lorem_ipsum_dolor_sit_amet),
                getString(R.string.lorem_ipsum_dolor_sit_amet),
                getString(R.string.lorem_ipsum_dolor_sit_amet));
        List<String> contents = Arrays.asList(
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_n_sed_do_eiusmod_tempor_incididunt_ut_labore_et_dolore_n_magna_aliqua),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_n_sed_do_eiusmod_tempor_incididunt_ut_labore_et_dolore_n_magna_aliqua),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_n_sed_do_eiusmod_tempor_incididunt_ut_labore_et_dolore_n_magna_aliqua),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_n_sed_do_eiusmod_tempor_incididunt_ut_labore_et_dolore_n_magna_aliqua),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_n_sed_do_eiusmod_tempor_incididunt_ut_labore_et_dolore_n_magna_aliqua),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_n_sed_do_eiusmod_tempor_incididunt_ut_labore_et_dolore_n_magna_aliqua)
        );

        adapter = new FAQAdapter(getActivity(), titles, contents);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.reVGeneral.setLayoutManager(linearLayoutManager);
        binding.reVGeneral.setAdapter(adapter);
    }
}
