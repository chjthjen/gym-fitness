package com.example.gymfitness.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.data.Exercise;
import com.example.gymfitness.databinding.RoundItemBinding;

import java.util.List;


public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.MyViewHolder> {

    private List<Exercise> exercises;

    public ExerciseAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RoundItemBinding binding=RoundItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(exercises.get(position));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RoundItemBinding binding;

        public MyViewHolder(@NonNull RoundItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        public void bindData(Exercise exercise){
            binding.icRound.setImageResource(exercise.getIcon());
            binding.tvTitle.setText(exercise.getTitle());
            binding.tvTime.setText(exercise.getTime());
            binding.tvRepetition.setText(exercise.getRoundedRepetition());
        }
    }
}
