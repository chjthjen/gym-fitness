package com.example.gymfitness.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.databinding.DataBindingUtil;

import com.example.gymfitness.R;
import com.example.gymfitness.data.WorkoutTest;
import com.example.gymfitness.databinding.RecommandRvcItemBinding;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder> {

    private List<WorkoutTest> recommendations;

    public RecommendationAdapter(List<WorkoutTest> recommendations) {
        this.recommendations = recommendations;
    }

    @NonNull
    @Override
    public RecommendationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecommandRvcItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.recommand_rvc_item, parent, false);
        return new RecommendationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationViewHolder holder, int position) {
        WorkoutTest item = recommendations.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return recommendations.size();
    }

    public static class RecommendationViewHolder extends RecyclerView.ViewHolder {
        private RecommandRvcItemBinding binding;

        public RecommendationViewHolder(RecommandRvcItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(WorkoutTest item) {
            binding.setItem(item);
            binding.executePendingBindings(); // This will bind the data immediately
        }
    }
}
