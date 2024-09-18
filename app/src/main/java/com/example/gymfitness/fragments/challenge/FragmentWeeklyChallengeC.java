package com.example.gymfitness.fragments.challenge;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentExerciseDetailBinding;
import com.example.gymfitness.databinding.FragmentWeeklyChallengeCBinding;
import com.example.gymfitness.utils.UserData;
import com.example.gymfitness.viewmodels.SharedViewModel;

import java.util.Objects;

public class FragmentWeeklyChallengeC extends Fragment {

    private FragmentWeeklyChallengeCBinding binding;
    private SharedViewModel sharedViewModel;
    private String level;
    private String urlVideo;
    private ExoPlayer player;
    private Dialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_weekly_challenge_c,container,false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        level = UserData.getUserLevel(getContext());
        return binding.getRoot();
    }
    private void loadData() {
        sharedViewModel.getExerciseSelected().observe(getViewLifecycleOwner(), exercise -> {
            Glide.with(binding.thumbnailVideo.getContext())
                    .load(exercise.getExerciseThumb())
                    .placeholder(R.drawable.woman_helping_man_gym)
                    .error(R.drawable.woman_helping_man_gym)
                    .into(binding.thumbnailVideo);
            binding.tvExerciseName.setText(exercise.getExercise_name());
            binding.tvExerciseDuration.setText(exercise.getDuration() + " Seconds");
            binding.tvExerciseRep.setText(exercise.getRep() + " Rep");
            binding.tvExerciseLevel.setText(exercise.getLevel());
            urlVideo = exercise.getLink();
        });
    }
    private void playVideo(String url) {
        if (url != null && !url.isEmpty()) {
            player = new ExoPlayer.Builder(getContext()).build();
            player.addListener(new ExoPlayer.Listener() {
                @Override
                public void onPlaybackStateChanged(int state) {
                    if (state == ExoPlayer.STATE_READY && progressDialog != null) {
                        progressDialog.dismiss();
                    }
                }
            });
            binding.videoView.setPlayer(player);
            player.setMediaItem(MediaItem.fromUri(url));
            player.prepare();
            player.play();
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.play.setOnClickListener(v -> {
            binding.cardView.setVisibility(View.GONE);
            binding.headerLayout.setBackgroundColor(Color.parseColor("#FF000000"));
            binding.videoView.setVisibility(View.VISIBLE);
            // Show custom loading dialog
            progressDialog = new Dialog(getContext());
            progressDialog.setContentView(R.layout.custom_progess_dialog);
            progressDialog.setCancelable(false); // Optional
            progressDialog.show();
            playVideo(urlVideo);
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
