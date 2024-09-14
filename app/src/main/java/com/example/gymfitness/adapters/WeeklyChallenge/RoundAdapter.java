package com.example.gymfitness.adapters.WeeklyChallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;
import com.example.gymfitness.data.Exercise;
import com.example.gymfitness.databinding.RoundItemBinding;

import java.util.List;

public class RoundAdapter extends RecyclerView.Adapter<RoundAdapter.RoundViewHolder> {

    private List<Exercise> exercises;
    private Context context;

    public RoundAdapter(List<Exercise> exercises, Context context) {
        this.exercises = exercises;
        this.context = context;
    }

    @NonNull
    @Override
    public RoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RoundItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.round_item, parent, false);
        return new RoundViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoundViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.binding.setExercise(exercise); // Bind the exercise data to the layout
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    static class RoundViewHolder extends RecyclerView.ViewHolder {
        RoundItemBinding binding;

        public RoundViewHolder(@NonNull RoundItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
