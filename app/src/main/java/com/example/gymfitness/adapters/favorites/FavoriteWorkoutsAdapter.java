package com.example.gymfitness.adapters.favorites;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.gymfitness.helpers.FavoriteHelper;

import java.util.ArrayList;
import java.util.List;

public class FavoriteWorkoutsAdapter extends RecyclerView.Adapter<FavoriteWorkoutsAdapter.FavoriteWorkoutsViewHolder> {

    ArrayList<Workout> listWorkout = new ArrayList<>();

    public FavoriteWorkoutsAdapter(ArrayList<Workout> list){
        this.listWorkout = list;
    }
    private com.example.gymfitness.adapters.WorkoutAdapter.OnWorkoutListener listener;

    public interface OnWorkoutListener {
        void onItemClick(Workout workout);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeItem(int position) {
        listWorkout.remove(position);
        notifyItemRemoved(position);
    }
    public Workout getWorkoutAt(int position) {
        if (position >= 0 && position < listWorkout.size()) {
            return listWorkout.get(position);
        } else {
            return null;
        }
    }



    public void setOnItemClickListener(com.example.gymfitness.adapters.WorkoutAdapter.OnWorkoutListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public FavoriteWorkoutsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemWorkoutNonvideoBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_workout_nonvideo, parent, false);

        return new FavoriteWorkoutsViewHolder(binding);
    }

    static class FavoriteWorkoutsViewHolder extends RecyclerView.ViewHolder {
        private ItemWorkoutNonvideoBinding binding;
        public FavoriteWorkoutsViewHolder(@NonNull ItemWorkoutNonvideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Workout workout , com.example.gymfitness.adapters.WorkoutAdapter.OnWorkoutListener listener, Context context) {
            binding.setItem(workout);
            binding.executePendingBindings();

            Glide.with(binding.thumbnail.getContext())
                    .load(workout.getThumbnail())
                    .placeholder(R.drawable.woman_helping_man_gym)
                    .error(R.drawable.woman_helping_man_gym)
                    .into(binding.thumbnail);
//                binding.star.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        FavoriteHelper.setFavorite(workout,v.getContext(), binding.star);
//                        //notify list changed
//                    }
//                });

            FavoriteHelper.checkFavorite(workout, context, binding.star);

            if (listener != null) {
                itemView.setOnClickListener(v -> listener.onItemClick(workout));
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteWorkoutsViewHolder holder, int position) {
        Workout workout = listWorkout.get(position);
        Context context = holder.itemView.getContext();
        holder.bind(workout, listener, context);
        holder.binding.star.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                FavoriteHelper.setFavorite(workout,v.getContext(), holder.binding.star);
                listWorkout.remove(workout);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listWorkout == null ? 0 : listWorkout.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setWorkoutList(ArrayList<Workout> workouts) {
        this.listWorkout.clear();
        this.listWorkout.addAll(workouts);
        notifyDataSetChanged();
    }

}


