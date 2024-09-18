package com.example.gymfitness.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.WorkoutLog;
import com.example.gymfitness.databinding.ItemActivitiesBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WorkoutLogAdapter extends RecyclerView.Adapter<WorkoutLogAdapter.WorkoutLogViewHolder> {

    private List<WorkoutLog> workoutLogs;

    public WorkoutLogAdapter(List<WorkoutLog> workoutLogs) {
        this.workoutLogs = workoutLogs;
    }

    @NonNull
    @Override
    public WorkoutLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemActivitiesBinding binding = ItemActivitiesBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new WorkoutLogViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutLogViewHolder holder, int position) {
        WorkoutLog log = workoutLogs.get(position);
        holder.bind(log);
    }

    @Override
    public int getItemCount() {
        return workoutLogs.size();
    }

    public void updateData(List<WorkoutLog> newLogs) {
        this.workoutLogs.clear();
        this.workoutLogs.addAll(newLogs);
        notifyDataSetChanged();
    }

    class WorkoutLogViewHolder extends RecyclerView.ViewHolder {
        private ItemActivitiesBinding binding;

        public WorkoutLogViewHolder(@NonNull ItemActivitiesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(WorkoutLog workoutlog) {
            binding.tvkcal.setText(workoutlog.getKcal() + " Kcal");
            binding.tvworkoutlogname.setText(workoutlog.getWorkout_name());
            String pattern = "dd/MM/yyyy";
            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat(pattern);
            Date date = workoutlog.getDate();
            String dateStr = df.format(date);
            binding.tvworkoutlogday.setText(dateStr);
            binding.tvworkoutlogmin.setText(workoutlog.getTotalTime() + " Mins");
            binding.imgkcal.setImageResource(R.drawable.ic_calories_home);
            binding.imgWorkout.setImageResource(R.drawable.ic_work_out_wc);
            binding.imgClock.setImageResource(R.drawable.purple_clock);
        }
    }
}
