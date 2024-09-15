package com.example.gymfitness.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.databinding.ExerciseForRoutineItemBinding;

import java.util.List;

public class ExerciseForOwnRoutineAdapter extends BaseAdapter {
    private List<Exercise> exerciseList;
    private LayoutInflater inflater;
    private ExerciseAddListener exerciseAddListener;

    public ExerciseForOwnRoutineAdapter(List<Exercise> exerciseList, LayoutInflater inflater, ExerciseAddListener exerciseAddListener) {
        this.exerciseList = exerciseList;
        this.inflater = inflater;
        this.exerciseAddListener = exerciseAddListener;
    }

    @Override
    public int getCount() {
        return exerciseList.size();
    }

    @Override
    public Object getItem(int position) {
        return exerciseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ExerciseForRoutineItemBinding binding;

        if (view == null) {
           binding = ExerciseForRoutineItemBinding.inflate(inflater, viewGroup, false);
           view = binding.getRoot();
           view.setTag(binding);
        }
        else {
            binding = (ExerciseForRoutineItemBinding) view.getTag();
        }

        Exercise exercise = exerciseList.get(position);
        binding.setItem(exercise);

        Glide.with(binding.thumbnail.getContext())
                        .load(exercise.getExerciseThumb())
                        .placeholder(R.drawable.woman_help_home)
                        .into(binding.thumbnail);

        binding.btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exerciseAddListener != null) {
                    exerciseAddListener.onExerciseAdded(exercise);
                }
            }
        });
        return view;
    }

    public interface ExerciseAddListener {
        void onExerciseAdded(Exercise exercise);
    }
}
