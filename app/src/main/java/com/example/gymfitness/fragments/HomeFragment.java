package com.example.gymfitness.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.home.ArticlesTipsRCVAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.adapters.home.RecommendExRCVApdater;
import com.example.gymfitness.data.WorkoutTest;
import com.example.gymfitness.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecommendExRCVApdater recommendExRCVApdater;
    private ArrayList<WorkoutTest> list;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager2;
    private ArticlesTipsRCVAdapter articlesTipsRCVAdapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    private FirebaseAuth auth;
    private GoogleSignInClient mGoogleSignInClient;
    private NavController navController;
    Button btnLogout;

    private ExecutorService executorService;



    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        executorService = Executors.newFixedThreadPool(3);
        setupRecyclerView();

        return view;
    }
    private void setupRecyclerView() {
        WorkoutTest workoutTest = new WorkoutTest();
        ArrayList<WorkoutTest> list = workoutTest.makeList();
        executorService.execute(() -> {
            getActivity().runOnUiThread(() -> {
                recommendExRCVApdater = new RecommendExRCVApdater(list);
                layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                binding.rcvRecommendations.setLayoutManager(layoutManager);
                binding.rcvRecommendations.setAdapter(recommendExRCVApdater);
            });
        });

        executorService.execute(() -> {
            getActivity().runOnUiThread(() -> {
                articlesTipsRCVAdapter = new ArticlesTipsRCVAdapter(list);
                layoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                binding.rcvArticlesTips.setLayoutManager(layoutManager2);
                binding.rcvArticlesTips.setAdapter(articlesTipsRCVAdapter);
            });
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
