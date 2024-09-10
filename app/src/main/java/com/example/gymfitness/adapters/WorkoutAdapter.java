package com.example.gymfitness.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_workout_item_nonvideo, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        // No data binding needed for this example
    }

    @Override
    public int getItemCount() {
        return 10; // Show 10 items for testing
    }

    static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

