package com.example.gymfitness.adapters.challenges;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.adapters.WorkoutAdapter;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.ItemWorkoutNonvideoBinding;

import java.util.ArrayList;

public class ChallengesAndCompetitionsRCVAdapter extends RecyclerView.Adapter<ChallengesAndCompetitionsRCVAdapter.ChallengesAndCompetitionsViewHolder> {
    ArrayList<Workout> listWorkout = new ArrayList<>();
    public ChallengesAndCompetitionsRCVAdapter(ArrayList<Workout> list) {
        this.listWorkout = list;
    }
    private OnChallengesAndCompetitionsListener listener;

    public interface OnChallengesAndCompetitionsListener {
        void onItemClick(Workout workout);
    }
    public void setOnItemClickListener(OnChallengesAndCompetitionsListener listener) {
        this.listener = listener;
    }
    public void setWorkoutList(ArrayList<Workout> workouts) {
        this.listWorkout.clear();
        this.listWorkout.addAll(workouts);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ChallengesAndCompetitionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemWorkoutNonvideoBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_workout_nonvideo, parent, false);
        return new ChallengesAndCompetitionsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengesAndCompetitionsViewHolder holder, int position) {
        Workout workout = listWorkout.get(position);
        holder.bind(workout,listener);
    }

    @Override
    public int getItemCount() {
        return listWorkout == null ? 0 : listWorkout.size();
    }

    static  class ChallengesAndCompetitionsViewHolder extends RecyclerView.ViewHolder {
        private ItemWorkoutNonvideoBinding binding;
        public ChallengesAndCompetitionsViewHolder(@NonNull ItemWorkoutNonvideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Workout workout , OnChallengesAndCompetitionsListener listener) {
            binding.setItem(workout);
            binding.executePendingBindings();

            Glide.with(binding.thumbnail.getContext())
                    .load(workout.getThumbnail())
                    .placeholder(R.drawable.woman_helping_man_gym)
                    .error(R.drawable.woman_helping_man_gym)
                    .into(binding.thumbnail);
            // set on click
            if (listener != null) {
                itemView.setOnClickListener(v -> listener.onItemClick(workout));
            }
        }
    }
}
