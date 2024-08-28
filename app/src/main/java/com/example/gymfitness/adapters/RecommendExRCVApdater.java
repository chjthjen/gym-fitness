package com.example.gymfitness.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;
import com.example.gymfitness.data.WorkoutTest;
import com.example.gymfitness.databinding.RecommandRvItemBinding;

import java.util.ArrayList;

public class RecommendExRCVApdater extends RecyclerView.Adapter<RecommendExRCVApdater.MyViewHolder> {
    private ArrayList<WorkoutTest> list;


    public RecommendExRCVApdater(ArrayList<WorkoutTest> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecommandRvItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.recommand_rv_item,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WorkoutTest workoutItem = list.get(position);
        holder.bind(workoutItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private RecommandRvItemBinding binding;
        public MyViewHolder(RecommandRvItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(WorkoutTest item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }
}
