package com.example.gymfitness.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.databinding.FragmentExerciseDetailBinding;
import com.example.gymfitness.utils.UserData;
import com.example.gymfitness.viewmodels.SharedViewModel;

import java.util.Objects;

public class ExerciseDetailFragment extends Fragment {

    FragmentExerciseDetailBinding exerciseDetailBinding;
    SharedViewModel sharedViewModel;
    private String level;
    private String urlVideo;
    private ExoPlayer player;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        exerciseDetailBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_exercise_detail, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        level = UserData.getUserLevel(getContext());

        return exerciseDetailBinding.getRoot();
    }

    private void loadData()
    {
        sharedViewModel.getExerciseSelected().observe(getViewLifecycleOwner(), new Observer<Exercise>() {
            @Override
            public void onChanged(Exercise exercise) {
                Glide.with(exerciseDetailBinding.thumbnail.getContext())
                        .load(exercise.getExerciseThumb())
                        .placeholder(R.drawable.woman_helping_man_gym)
                        .error(R.drawable.woman_helping_man_gym)
                        .into(exerciseDetailBinding.thumbnail);
                exerciseDetailBinding.exerciseName.setText(exercise.getExercise_name());
                exerciseDetailBinding.duration.setText(String.valueOf(exercise.getDuration()) + " Seconds");
                exerciseDetailBinding.rep.setText(String.valueOf(exercise.getRep()) + " Rep");
                exerciseDetailBinding.level.setText(exercise.getLevel());
                urlVideo = exercise.getLink();
            }
        });

    }

    private void playVideo(String url)
    {
        if (url != null && !url.isEmpty()) {
            player = new ExoPlayer.Builder(getContext()).build();
            exerciseDetailBinding.videoView.setPlayer(player);

            MediaItem mediaItem = MediaItem.fromUri(url);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exerciseDetailBinding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exerciseDetailBinding.cardView.setVisibility(v.GONE);
                exerciseDetailBinding.headerLayout.setBackgroundColor(Color.parseColor("#FF000000"));
                exerciseDetailBinding.videoView.setVisibility(v.VISIBLE);
                exerciseDetailBinding.controlButtons.setVisibility(v.VISIBLE);
                playVideo(urlVideo);
                controlButton();
            }
        });
    }

    private void controlButton()
    {
        exerciseDetailBinding.playPauseButton.setOnClickListener(v -> {
            if (player.isPlaying()) {
                player.pause();
            } else {
                player.play();
            }
        });

        exerciseDetailBinding.fastForwardButton.setOnClickListener(v -> {
            long currentPosition = player.getCurrentPosition();
            long seekPosition = currentPosition + 1000;
            player.seekTo(seekPosition);
        });

        exerciseDetailBinding.rewindButton.setOnClickListener(v -> {
            long currentPosition = player.getCurrentPosition();
            long seekPosition = Math.max(currentPosition - 1000, 0);
            player.seekTo(seekPosition);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(level);
        loadData();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}