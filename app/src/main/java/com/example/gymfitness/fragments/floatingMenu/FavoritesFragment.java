package com.example.gymfitness.fragments.floatingMenu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.home.ArticlesTipsRCVAdapter;
import com.example.gymfitness.adapters.resources.ArticleResourceAdapter;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.databinding.FragmentFavoritesBinding;
import com.example.gymfitness.viewmodels.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FavoritesFragment extends Fragment {
    FragmentFavoritesBinding binding;
    ArticleViewModel articleViewModel;
    ArticleResourceAdapter articleResourceAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false);
        articleViewModel = new ArticleViewModel();
        articleResourceAdapter = new ArticleResourceAdapter(new ArrayList<>());
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpArticlesRecyclerView();
    }

    void setUpArticlesRecyclerView() {
        binding.rcvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.rcvFavorites.setAdapter(articleResourceAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        articleViewModel.loadArticlesByFavorite(getContext());
        articleViewModel.getArticles().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.getClass().getSimpleName()) {
                case "Loading":
                    Log.d("TAG", "onViewCreated: Loading");
                    break;
                case "Success":
                    Log.d("TAG", "onViewCreated: Success");
                    articleResourceAdapter.setArticleList(resource.getData());
                    break;
                case "Error":
                    Log.d("TAG", "onViewCreated: Error");
                    break;
            }
        });
    }
}
