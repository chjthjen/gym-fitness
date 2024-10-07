package com.example.gymfitness.adapters.exercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.databinding.ExerciseInRoutineItemBinding;
import com.example.gymfitness.helpers.FavoriteHelper;
import com.example.gymfitness.viewmodels.SharedViewModel;

import java.util.List;

public class ExerciseInOwnRoutineAdapter extends RecyclerView.Adapter<ExerciseInOwnRoutineAdapter.ExerciseViewHolder> {

    private final List<Exercise> exerciseList;
    private final LayoutInflater inflater;
    private final ExerciseRemoveListener exerciseRemoveListener;
    private final SharedViewModel sharedViewModel;
    private final Context context;

    public ExerciseInOwnRoutineAdapter(Context context, List<Exercise> exerciseList, ExerciseRemoveListener exerciseRemoveListener, SharedViewModel sharedViewModel) {
        this.context = context;
        this.exerciseList = exerciseList;
        this.inflater = LayoutInflater.from(context);
        this.exerciseRemoveListener = exerciseRemoveListener;
        this.sharedViewModel = sharedViewModel;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExerciseInRoutineItemBinding binding = ExerciseInRoutineItemBinding.inflate(inflater, parent, false);
        return new ExerciseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.bind(exercise);
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public void setExercises(List<Exercise> exercises) {
        this.exerciseList.clear();
        this.exerciseList.addAll(exercises);
        notifyDataSetChanged();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private final ExerciseInRoutineItemBinding binding;

        ExerciseViewHolder(ExerciseInRoutineItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Exercise exercise) {
            binding.setItem(exercise);
            binding.executePendingBindings();

            Glide.with(binding.thumbnail.getContext())
                    .load(exercise.getExerciseThumb())
                    .placeholder(R.drawable.woman_help_home)
                    .into(binding.thumbnail);

                binding.imgPlayvideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    exerciseRemoveListener.onExerciseRemoved(exercise);
                }
            });

            binding.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavController navController = Navigation.findNavController(view);
                    sharedViewModel.selectExercise(exercise);
                    navController.navigate(R.id.action_ownRoutineFragment_to_exerciseDetail);
                }
            });

            FavoriteHelper.checkFavorite(exercise, context, binding.imgStar);
            binding.imgStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FavoriteHelper.setFavorite(exercise, v.getContext(), binding.imgStar);
                }
            });
        }
    }

    public interface ExerciseRemoveListener {
        void onExerciseRemoved(Exercise exercise);
    }
}
