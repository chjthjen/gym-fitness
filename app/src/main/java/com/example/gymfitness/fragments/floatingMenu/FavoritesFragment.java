package com.example.gymfitness.fragments.floatingMenu;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.FavoriteRCVAdapter;
import com.example.gymfitness.adapters.WorkoutAdapter;
import com.example.gymfitness.data.DAO.FavoriteArticleDAO;
import com.example.gymfitness.data.database.FitnessDB;
import com.example.gymfitness.data.entities.FavoriteArticle;
import com.example.gymfitness.databinding.FragmentFavoritesBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

    // 0: all, 1: video, 2: article
    private int current_sort = 0;
    FragmentFavoritesBinding binding;

    private ColorStateList colorStateList;
    private FavoriteRCVAdapter favoriteRCVAdapter;
    private List<FavoriteArticle> favoriteArticleList = new ArrayList<>();


    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnAll.setOnClickListener(v -> sortChange(0));
        binding.btnVideo.setOnClickListener(v -> sortChange(1));
        binding.btnArticle.setOnClickListener(v -> sortChange(2));
        favoriteRCVAdapter = new FavoriteRCVAdapter();
//        WorkoutAdapter workoutAdapter = new WorkoutAdapter();
//        binding.rcvFavorites.setAdapter(workoutAdapter);
//        binding.rcvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void sortChange(int pos) {
        colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.white));
        // set button is not selected
        if (current_sort == 0) {
            binding.btnAll.setBackgroundTintList(colorStateList);
            binding.btnAll.setTextColor(ContextCompat.getColor(getContext(), R.color.purple));


        } else if (current_sort == 1) {
            binding.btnVideo.setBackgroundTintList(colorStateList);
            binding.btnVideo.setTextColor(ContextCompat.getColor(getContext(), R.color.purple));

        } else {
            binding.btnArticle.setBackgroundTintList(colorStateList);
            binding.btnArticle.setTextColor(ContextCompat.getColor(getContext(), R.color.purple));

        }

        // set selected button
        colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.limegreen));
        if (pos == 0) {
            binding.btnAll.setBackgroundTintList(colorStateList);
            binding.btnAll.setTextColor(ContextCompat.getColor(getContext(), R.color.black));


        } else if (pos == 1) {
            binding.btnVideo.setBackgroundTintList(colorStateList);
            binding.btnVideo.setTextColor(ContextCompat.getColor(getContext(), R.color.black));


        } else {
            binding.btnArticle.setBackgroundTintList(colorStateList);
            binding.btnArticle.setTextColor(ContextCompat.getColor(getContext(), R.color.black));


            ExecutorService executorService;
            executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                favoriteArticleList = FitnessDB.getInstance(getContext()).favoriteArticleDAO().getAll();

            });
            favoriteRCVAdapter.setDataArticle(favoriteArticleList);
            LinearLayoutManager linearLayoutManager;
            linearLayoutManager = new LinearLayoutManager(getContext());
            binding.rcvFavorites.setLayoutManager(linearLayoutManager);
        }


        // update current sort
        current_sort = pos;
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Favorites");

    }
}