package com.example.gymfitness.adapters.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;
import com.example.gymfitness.data.WorkoutTest;
import com.example.gymfitness.databinding.RcvWorkoutItemVideoBinding;

import java.util.ArrayList;

public class RecommendationVideoAdapter extends RecyclerView.Adapter<RecommendationVideoAdapter.ViewHolder> {

    private ArrayList<WorkoutTest> workoutList;

    public RecommendationVideoAdapter(ArrayList<WorkoutTest> workoutList) {
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RcvWorkoutItemVideoBinding binding = RcvWorkoutItemVideoBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutTest workout = workoutList.get(position);
        holder.binding.setItem(workout);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final RcvWorkoutItemVideoBinding binding;

        public ViewHolder(RcvWorkoutItemVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
