package com.example.gymfitness.fragments.WeeklyChallenge;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.databinding.FragmentCongratulationBinding;
import com.example.gymfitness.databinding.FragmentWeeklyChallengeCBinding;
import com.example.gymfitness.helpers.FavoriteHelper;
import com.example.gymfitness.helpers.ProgressTrackHelper;
import com.example.gymfitness.viewmodels.SharedViewModel;

public class WeeklyChallengeDFragment extends Fragment {
    FragmentCongratulationBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_congratulation,container,false);
        return binding.getRoot();
    }

}
