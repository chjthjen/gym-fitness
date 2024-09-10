package com.example.gymfitness.fragments.floatingMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.RecommendationAdapter;
import com.example.gymfitness.adapters.search.RecommendationVideoAdapter;
import com.example.gymfitness.data.WorkoutTest;

import java.util.ArrayList;

public class AllSearchFragment extends Fragment {
    private RecyclerView recyclerViewRecommendations;
    private RecyclerView recyclerViewFavorites;
    private RecommendationAdapter recommendationAdapter;
    private RecommendationVideoAdapter videoAdapter;

    private ArrayList<WorkoutTest> workoutList;

    public AllSearchFragment() {
        super(R.layout.fragment_all_search);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_search, container, false);

        // Khởi tạo RecyclerView cho recommendations
        recyclerViewRecommendations = view.findViewById(R.id.fas_rcv_recommendations);
        recyclerViewRecommendations.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Giả lập dữ liệu cho danh sách workout
        workoutList = WorkoutTest.makeList();

        // Khởi tạo adapter và gắn vào RecyclerView recommendations
        recommendationAdapter = new RecommendationAdapter(workoutList);
        recyclerViewRecommendations.setAdapter(recommendationAdapter);

        // Khởi tạo RecyclerView cho favorites
        recyclerViewFavorites = view.findViewById(R.id.rcv_favorites);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Khởi tạo adapter cho favorites
        videoAdapter = new RecommendationVideoAdapter(workoutList);
        recyclerViewFavorites.setAdapter(videoAdapter);

        return view;
    }
}
