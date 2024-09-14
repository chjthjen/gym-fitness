package com.example.gymfitness.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.ItemWorkoutNonvideoBinding;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    ArrayList<Workout> listWorkout = new ArrayList<>();

    public WorkoutAdapter(ArrayList<Workout> list){
        this.listWorkout = list;
    }
    private OnWorkoutListener listener;

    public interface OnWorkoutListener {
        void onItemClick(Workout workout);
    }

    public void setOnItemClickListener(OnWorkoutListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemWorkoutNonvideoBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.item_workout_nonvideo, parent, false);
        return new WorkoutViewHolder(binding);
    }



    static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        private ItemWorkoutNonvideoBinding binding;
        public WorkoutViewHolder(@NonNull ItemWorkoutNonvideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Workout workout , OnWorkoutListener listener) {
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

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout workout = listWorkout.get(position);
        holder.bind(workout,listener);

    }

    @Override
    public int getItemCount() {
        return listWorkout == null ? 0 : listWorkout.size();
    }


    public void setWorkoutList(ArrayList<Workout> workouts) {
        this.listWorkout.clear();
        this.listWorkout.addAll(workouts);
        notifyDataSetChanged();
    }




}

