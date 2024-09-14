package com.example.gymfitness.adapters.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;
import com.example.gymfitness.data.WorkoutTest;
import com.example.gymfitness.databinding.ArticlesTipsRvItemBinding;

import java.util.ArrayList;

public class  ArticlesTipsRCVAdapter extends RecyclerView.Adapter<ArticlesTipsRCVAdapter.MyViewHolder> {
    private ArrayList<WorkoutTest> list;

    public ArticlesTipsRCVAdapter(ArrayList<WorkoutTest> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ArticlesTipsRCVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ArticlesTipsRvItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.articles_tips_rv_item,parent,false);
        return new ArticlesTipsRCVAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesTipsRCVAdapter.MyViewHolder holder, int position) {
        WorkoutTest workoutItem = list.get(position);
        holder.bind(workoutItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ArticlesTipsRvItemBinding binding;
        public MyViewHolder(ArticlesTipsRvItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(WorkoutTest item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }
}
