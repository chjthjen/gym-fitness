package com.example.gymfitness.fragments.challenge;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.entities.Round;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.FragmentExerciseDetailBinding;
import com.example.gymfitness.databinding.FragmentWeeklyChallengeCBinding;
import com.example.gymfitness.helpers.FavoriteHelper;
import com.example.gymfitness.helpers.ProgressTrackHelper;
import com.example.gymfitness.utils.UserData;
import com.example.gymfitness.viewmodels.ExerciseRoutineViewModel;
import com.example.gymfitness.viewmodels.SharedViewModel;

import java.util.Objects;

public class FragmentWeeklyChallengeC extends Fragment {

    private FragmentWeeklyChallengeCBinding binding;
    private SharedViewModel sharedViewModel;
    private String level;
    private String urlVideo;
    private ExoPlayer player;
    private Dialog progressDialog;
    private ProgressTrackHelper progressTrackHelper;
    private NavController navController;
    private Workout getWorkout = new Workout();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_weekly_challenge_c,container,false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return binding.getRoot();
    }
    private  void loadWorkoutData() {
        sharedViewModel.getSelected().observe(getViewLifecycleOwner(), new Observer<Workout>() {
            @Override
            public void onChanged(Workout workout) {
                getWorkout = workout;
            }
        });
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
            level = exercise.getLevel();
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
                    if (state == ExoPlayer.STATE_ENDED) {
                        sharedViewModel.select(getWorkout);
                        navController.navigate(R.id.action_fragmentWeeklyChallengeC_to_congratulationFragment);
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
        navController = Navigation.findNavController(view);
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
            // save progess
            saveProgress();
        });
        // set favorite
        Exercise exerciseFavorite = sharedViewModel.getExerciseSelected().getValue();
        binding.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteHelper.setFavorite(exerciseFavorite,v.getContext(), binding.star);
            }
        });

        FavoriteHelper.checkFavorite(exerciseFavorite, getContext(), binding.star);
    }

    private void saveProgress() {
        progressTrackHelper = new ProgressTrackHelper();
        Workout selectedValue = sharedViewModel.getSelected().getValue();
        Exercise exerciseSelectedValue = sharedViewModel.getExerciseSelected().getValue();

        if (selectedValue != null && exerciseSelectedValue != null) {
            try {
                progressTrackHelper.SaveProgress(selectedValue, exerciseSelectedValue,getContext());
                Log.d("ProgressTrackHelper", "Progress saved successfully");
            } catch (Exception e) {
                Log.e("ProgressTrackHelper", "Error saving progress", e);
            }
        } else {
            Log.w("ProgressTrackHelper", "Selected value, exercise selected value, or activity is null");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        loadWorkoutData();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(level);
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
