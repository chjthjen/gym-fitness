package com.example.gymfitness.fragments.resources;

import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.ArticleDetailAdapter;
import com.example.gymfitness.databinding.FragmentArticleDetailBinding;
import com.example.gymfitness.viewmodels.ArticleDetailViewModel;

import java.util.ArrayList;
import java.util.List;

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

        List<ArticleDetailViewModel> articleDetailList = new ArrayList<>();
        articleDetailList.add(new ArticleDetailViewModel("Plan Your Routine:", "Before starting any workout, plan your routine for the week. Focus on different muscle groups on different days to allow for adequate rest and recovery."));
        articleDetailList.add(new ArticleDetailViewModel("Warm-Up:", "Begin your workout with a proper warm-up session. This could include light cardio exercises like jogging or jumping jacks, as well as dynamic stretches to prepare your muscles for the upcoming workout."));

        adapter = new ArticleDetailAdapter(requireContext(), articleDetailList);
        binding.lvArticleDetail.setAdapter(adapter);

        return binding.getRoot();
    }
}
