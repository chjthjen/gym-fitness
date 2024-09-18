package com.example.gymfitness.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.WorkoutLog;
import com.example.gymfitness.databinding.FragmentWorkoutLogBinding;
import com.example.gymfitness.databinding.ItemActivitiesBinding;
import com.example.gymfitness.databinding.ItemDayBinding;
import com.google.firebase.database.collection.LLRBNode;

import java.util.Date;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.DayViewHolder> {

    private int selectedPosition = -1;
    private int previousSelectedPosition = -1;
    private List<String> days;
    private List<String> specialDays;
    private OnDayClickListener onDayClickListener;
    private String currentMonth;

    public CalendarAdapter(List<String> days, int selectedPosition, List<String> specialDays, String currentMonth, OnDayClickListener onDayClickListener) {
        this.days = days;
        this.selectedPosition = selectedPosition;
        this.specialDays = specialDays;
        this.currentMonth = currentMonth;
        this.onDayClickListener = onDayClickListener;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        holder.bind(days.get(position), position);
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    class DayViewHolder extends RecyclerView.ViewHolder {
        private ItemDayBinding binding;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemDayBinding.bind(itemView);
        }

        public void bind(String day, int position) {

            binding.tvDay.setText(day);

            boolean isSpecial = specialDays.contains(day);
            if (selectedPosition == position) {
                if (binding.tvDay.getText().toString().equals("")) {
                    binding.tvDay.setBackgroundResource(android.R.color.transparent);
                    binding.tvDay.setSelected(false);
                } else {
                    binding.tvDay.setBackgroundResource(R.drawable.day_background);
                    binding.tvDay.setTextColor(Color.WHITE);
                    binding.tvDay.setSelected(true);
                }
            } else {
                if (isSpecial) {
                    binding.tvDay.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.purple));
                } else {
                    binding.tvDay.setBackgroundResource(android.R.color.transparent);
                    binding.tvDay.setTextColor(Color.BLACK);
                }
                binding.tvDay.setSelected(false);
            }

            itemView.setOnClickListener(v -> {
                if (selectedPosition != position) {
                    previousSelectedPosition = selectedPosition;
                    selectedPosition = position;

                    String selectedDay = days.get(position);  // Định dạng của ngày có thể khác
                    // convert month to int
                    switch (currentMonth) {
                        case "January":
                            currentMonth = "1";
                            break;
                        case "February":
                            currentMonth = "2";
                            break;
                        case "March":
                            currentMonth = "3";
                            break;
                        case "April":
                            currentMonth = "4";
                            break;
                        case "May":
                            currentMonth = "5";
                            break;
                        case "June":
                            currentMonth = "6";
                            break;
                        case "July":
                            currentMonth = "7";
                            break;
                        case "August":
                            currentMonth = "8";
                            break;
                        case "September":
                            currentMonth = "9";
                            break;
                        case "October":
                            currentMonth = "10";
                            break;
                        case "November":
                            currentMonth = "11";
                            break;
                        case "December":
                            currentMonth = "12";
                            break;
                    }

                    onDayClickListener.onDayClick(selectedDay, currentMonth);

                    notifyItemChanged(previousSelectedPosition);
                    notifyItemChanged(selectedPosition);
                }
            });
        }

    }

    public interface OnDayClickListener {
        void onDayClick(String day, String month);
    }
}
