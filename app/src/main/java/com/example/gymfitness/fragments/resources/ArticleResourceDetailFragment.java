package com.example.gymfitness.fragments.resources;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.adapters.resources.ArticleDetailAdapter;
import com.example.gymfitness.databinding.FragmentArticleDetailBinding;
import com.example.gymfitness.databinding.FragmentArticleResourceDetailBinding;
import com.example.gymfitness.databinding.FragmentResourcesBinding;
import com.example.gymfitness.viewmodels.ArticleDetailViewModel;
import com.example.gymfitness.viewmodels.ArticleResourceViewModel;

import java.util.ArrayList;

public class ArticleResourceDetailFragment extends Fragment {

    private ArticleDetailAdapter adapter;
    private ArticleResourceViewModel articleResourceViewModel;

    public ArticleResourceDetailFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentArticleResourceDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_resource_detail, container, false);

        articleResourceViewModel = new ViewModelProvider(this).get(ArticleResourceViewModel.class);
        binding.setViewModel(articleResourceViewModel);
        binding.setLifecycleOwner(this);

        adapter = new ArticleDetailAdapter(requireContext(), new ArrayList<>());
        binding.lvArticleDetail.setAdapter(adapter);

        if (getArguments() != null) {
            String articleTitle = getArguments().getString("articleTitle");
            articleResourceViewModel.loadArticleDetails(articleTitle);
        }

        articleResourceViewModel.getArticleDetails().observe(getViewLifecycleOwner(), details -> {
            adapter.setArticleDetails(details);
        });

        articleResourceViewModel.getThumbnail().observe(getViewLifecycleOwner(), thumbnail -> {
            Glide.with(requireContext()).load(thumbnail).into(binding.imgArticle);
        });

        return binding.getRoot();
    }
}