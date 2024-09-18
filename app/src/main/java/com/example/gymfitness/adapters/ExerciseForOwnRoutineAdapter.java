package com.example.gymfitness.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.databinding.ExerciseForRoutineItemBinding;
import com.example.gymfitness.helpers.FavoriteHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExerciseForOwnRoutineAdapter extends BaseAdapter {
    private List<Exercise> allExercises;
    private Set<Integer> addedExerciseIds; // id các bai tap duoc add vao round
    private final LayoutInflater inflater;
    private final ExerciseAddListener exerciseAddListener;
    private final int roundId;

    public ExerciseForOwnRoutineAdapter(List<Exercise> allExercises, LayoutInflater inflater,
                                        ExerciseAddListener exerciseAddListener, int roundId) {
        this.allExercises = allExercises;
        this.inflater = inflater;
        this.exerciseAddListener = exerciseAddListener;
        this.roundId = roundId;
        this.addedExerciseIds = new HashSet<>();
    }

    // Cập nhật danh sách tất cả các bài tập
    public void setAllExercises(List<Exercise> allExercises) {
        this.allExercises = allExercises;
        notifyDataSetChanged();
    }

    // Cập nhật danh sách các bài tập đã thêm vào Round
    public void setAddedExerciseIds(Set<Integer> addedExerciseIds) {
        this.addedExerciseIds = addedExerciseIds;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return allExercises.size();
    }

    @Override
    public Object getItem(int position) {
        return allExercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return allExercises.get(position).getExercise_id();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ExerciseForRoutineItemBinding binding;

        if (view == null) {
            binding = ExerciseForRoutineItemBinding.inflate(inflater, viewGroup, false);
            view = binding.getRoot();
            view.setTag(binding);
        } else {
            binding = (ExerciseForRoutineItemBinding) view.getTag();
        }

        Exercise exercise = allExercises.get(position);
        binding.setItem(exercise);

        Glide.with(binding.thumbnail.getContext())
                .load(exercise.getExerciseThumb())
                .placeholder(R.drawable.woman_help_home)
                .into(binding.thumbnail);

        // Kiểm tra xem bài tập đã được thêm vào Round hay chưa
        if (addedExerciseIds.contains(exercise.getExercise_id())) {
            binding.btnAddExercise.setImageResource(R.drawable.bot_video_check);
            binding.btnAddExercise.setEnabled(false);
        } else {
            binding.btnAddExercise.setImageResource(R.drawable.bot_video_add);
            binding.btnAddExercise.setEnabled(true);
        }

        binding.btnAddExercise.setOnClickListener(view1 -> {
            exerciseAddListener.onExerciseAdded(exercise);
        });

        FavoriteHelper.checkFavorite(exercise, view.getContext(), binding.imgStar);
        binding.imgStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteHelper.setFavorite(exercise,v.getContext(), binding.imgStar);
            }
        });
        return view;
    }

    public interface ExerciseAddListener {
        void onExerciseAdded(Exercise exercise);
    }
}
