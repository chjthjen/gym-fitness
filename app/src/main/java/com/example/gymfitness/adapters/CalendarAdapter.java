package com.example.gymfitness.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.DayViewHolder>{

    private int selectedPosition = -1;
    private int previousSelectedPosition = -1;
    private List<String> days;

    public CalendarAdapter(List<String> days, int selectedPosition) {
        this.days = days;
        this.selectedPosition = selectedPosition;
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

    class DayViewHolder extends RecyclerView.ViewHolder{
        TextView dayTextView;
        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.tvDay);
        }
        public void bind(String day, int position) {
            dayTextView.setText(day);



            if (selectedPosition == position) {
                if(dayTextView.getText().toString().equals(""))
                {
                    dayTextView.setBackgroundResource(android.R.color.transparent);
                    dayTextView.setSelected(false);
                }
                else {
                    dayTextView.setBackgroundResource(R.drawable.day_background);
                    dayTextView.setTextColor(Color.WHITE);
                    dayTextView.setSelected(true);
                }
            } else {
                dayTextView.setBackgroundResource(android.R.color.transparent);
                dayTextView.setTextColor(Color.BLACK);
                dayTextView.setSelected(false);
            }

            itemView.setOnClickListener(v -> {
                if (selectedPosition != position) {
                    previousSelectedPosition = selectedPosition;
                    selectedPosition = position;

                    // Chỉ cập nhật lại mục đã chọn và mục trước đó
                    notifyItemChanged(previousSelectedPosition);
                    notifyItemChanged(selectedPosition);
                }
            });
        }

    }
}
