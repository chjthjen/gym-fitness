package com.example.gymfitness.fragments.resources;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.adapters.ArticleDetailAdapter;
import com.example.gymfitness.databinding.FragmentArticleDetailBinding;
import com.example.gymfitness.viewmodels.ArticleDetailViewModel;

import java.util.ArrayList;

public class ArticleDetailFragment extends Fragment {

    private ArticleDetailAdapter adapter;
    private ArticleDetailViewModel articleDetailViewModel;

    public ArticleDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentArticleDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_detail, container, false);

        articleDetailViewModel = new ViewModelProvider(this).get(ArticleDetailViewModel.class);
        binding.setViewModel(articleDetailViewModel);
        binding.setLifecycleOwner(this);

        adapter = new ArticleDetailAdapter(requireContext(), new ArrayList<>());
        binding.lvArticleDetail.setAdapter(adapter);

        if (getArguments() != null) {
            String articleTitle = getArguments().getString("articleTitle");
            articleDetailViewModel.loadArticleDetails(articleTitle);
        }

        articleDetailViewModel.getArticleDetails().observe(getViewLifecycleOwner(), details -> {
            adapter.setArticleDetails(details);
        });

        articleDetailViewModel.getThumbnail().observe(getViewLifecycleOwner(), thumbnail -> {
            Glide.with(requireContext()).load(thumbnail).into(binding.imgArticle);
        });

        return binding.getRoot();
    }
}