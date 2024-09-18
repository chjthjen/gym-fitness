package com.example.gymfitness.fragments.resources;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymfitness.R;
import com.example.gymfitness.adapters.home.RecommendExRCVApdater;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.FragmentWorkoutResourceBinding;
import com.example.gymfitness.viewmodels.HomeViewModel;
import com.example.gymfitness.viewmodels.SharedViewModel;
import androidx.databinding.DataBindingUtil;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkoutResourceFragment extends Fragment {

    private FragmentWorkoutResourceBinding binding;
    private RecommendExRCVApdater recommendExRCVApdater;
    private HomeViewModel homeViewModel;
    private ExecutorService executorService;
    private SharedViewModel sharedViewModel;

    public WorkoutResourceFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_workout_resource, container, false);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        //homeViewModel.setUserLevel(getContext());
        executorService = Executors.newFixedThreadPool(3);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        recommendExRCVApdater = new RecommendExRCVApdater(new ArrayList<>());

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = binding.rcvWorkoutResources;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(recommendExRCVApdater);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupRecyclerView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        // navigate
        //binding.imgWorkout.setOnClickListener(v -> navController.navigate(R.id.action_homeFragment_to_workoutFragment));
        //binding.imgCommunity.setOnClickListener(v -> navController.navigate(R.id.action_homeFragment_to_communityFragment2));
        //binding.imgProgess.setOnClickListener(v -> navController.navigate(R.id.action_homeFragment_to_progressTrackingMainFragment));
        //homeViewModel.loadWorkoutsByLevel();
        homeViewModel.loadRoundExercise();
        homeViewModel.getWorkouts().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.getClass().getSimpleName()) {
                case "Loading":
                    binding.progressBarWorkout.setVisibility(View.VISIBLE);
                    break;
                case "Success":
                    binding.progressBarWorkout.setVisibility(View.GONE);
                    recommendExRCVApdater.setWorkoutList(resource.getData());
                    break;
                case "Error":
                    binding.progressBarWorkout.setVisibility(View.GONE);
                    Log.d("hello", resource.getMessage());
                    break;
            }
        });

        recommendExRCVApdater.setOnItemClickListener(new RecommendExRCVApdater.OnWorkoutRCMListener() {
            @Override
            public void onItemClick(Workout workout) {
                sharedViewModel.select(workout);
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.roundExerciseResourceFragment);
                Log.d("WorkoutResourceFragment", "Selected workout: " + workout.toString());
            }
        });
    }


}
