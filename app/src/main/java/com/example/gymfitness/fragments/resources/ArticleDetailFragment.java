package com.example.gymfitness.fragments.resources;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.ArticleDetailAdapter;
import com.example.gymfitness.viewmodels.ArticleDetailViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;
    private List<ArticleDetailViewModel> articleDetailList;
    private ArticleDetailAdapter adapter;

    public ArticleDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticleDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleDetailFragment newInstance(String param1, String param2) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);

        articleDetailList = new ArrayList<>();
        articleDetailList.add(new ArticleDetailViewModel("Plan Your Routine:", "Before starting any workout, plan your routine for the week. Focus on different muscle groups on different days to allow for adequate rest and recovery."));
        articleDetailList.add(new ArticleDetailViewModel("Warm-Up:", "Begin your workout with a proper warm-up session. This could include light cardio exercises like jogging or jumping jacks, as well as dynamic stretches to prepare your muscles for the upcoming workout."));

        adapter = new ArticleDetailAdapter(requireContext(), articleDetailList);
        listView.setAdapter(adapter);
        return view;
    }
}