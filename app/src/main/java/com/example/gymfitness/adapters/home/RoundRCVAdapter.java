package com.example.gymfitness.adapters.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.Round;
import com.example.gymfitness.data.entities.Workout;
import com.example.gymfitness.databinding.RoundItemBinding;

import java.util.ArrayList;

public class RoundRCVAdapter extends RecyclerView.Adapter<RoundRCVAdapter.RoundHolder> {

    private ArrayList<Round> listRound;

    public RoundRCVAdapter(ArrayList<Round> list)
    {
        this.listRound = list;
    }

    @NonNull
    @Override
    public RoundRCVAdapter.RoundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RoundItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.round_item, parent, false);
        return new RoundRCVAdapter.RoundHolder(binding);
    }

    static class RoundHolder extends RecyclerView.ViewHolder {
        private RoundItemBinding binding;
        public RoundHolder(@NonNull RoundItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Round round) {
            binding.setRound(round);
            binding.executePendingBindings();
            ExerciseRCVAdapter exerciseAdapter = new ExerciseRCVAdapter(round.getExercises());
            binding.rcvExecercise.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
            binding.rcvExecercise.setAdapter(exerciseAdapter);

        }
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull RoundRCVAdapter.RoundHolder holder, int position) {
        Round round = listRound.get(position);
        holder.bind(round);
    }

    @Override
    public int getItemCount() {
        return listRound == null ? 0 : listRound.size();
    }

}
