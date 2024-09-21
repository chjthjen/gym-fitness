package com.example.gymfitness.fragments.search;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;

import com.example.gymfitness.adapters.CustomAdapterListViewWorkoutSearch;
import com.example.gymfitness.adapters.home.ArticlesTipsRCVAdapter;
import com.example.gymfitness.adapters.resources.ArticleResourceAdapter;
import com.example.gymfitness.data.entities.Article;

import com.example.gymfitness.utils.Resource;
import com.example.gymfitness.viewmodels.HomeViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ArticleSearchFragment extends Fragment {

    private ListView listView;
    private EditText edtSearch;
    private List<String> searchHistory;
    private CustomAdapterListViewWorkoutSearch adapter;
    private SharedPreferences sharedPreferences;

    private HomeViewModel articleViewModel;
    private RecyclerView rvArticleItem;
    private ArticleResourceAdapter articleAdapter;
    private NavController navController;

    public ArticleSearchFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_search, container, false);

        articleViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        listView = view.findViewById(R.id.listView);
        edtSearch = view.findViewById(R.id.edtSearchArticle);
        rvArticleItem = view.findViewById(R.id.rv_article_item);

        sharedPreferences = requireContext().getSharedPreferences("SearchHistory", getContext().MODE_PRIVATE);

        articleViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        searchHistory = loadSearchHistory();
        adapter = new CustomAdapterListViewWorkoutSearch(getActivity(), searchHistory, sharedPreferences);
        listView.setAdapter(adapter);

        articleAdapter = new ArticleResourceAdapter(new ArrayList<>());
        rvArticleItem.setLayoutManager(new LinearLayoutManager(getContext()));
        rvArticleItem.setAdapter(articleAdapter);

        articleViewModel.getArticles().observe(getViewLifecycleOwner(), new Observer<Resource<ArrayList<Article>>>() {
            @Override
            public void onChanged(Resource<ArrayList<Article>> resource) {
                if (resource instanceof Resource.Success) {
                    ArrayList<Article> articles = ((Resource.Success<ArrayList<Article>>) resource).getData();
                    articleAdapter.setArticleList(articles);
                    articleAdapter.notifyDataSetChanged();
                } else if (resource instanceof Resource.Error) {
                    String errorMessage = ((Resource.Error<ArrayList<Article>>) resource).getMessage();
                    Log.e("InitialLoadError", errorMessage);
                } else if (resource instanceof Resource.Loading) {
                }
            }
        });

        articleViewModel.getSearchArticles().observe(getViewLifecycleOwner(), new Observer<Resource<ArrayList<Article>>>() {
            @Override
            public void onChanged(Resource<ArrayList<Article>> resource) {
                if (resource instanceof Resource.Success) {
                    ArrayList<Article> articles = ((Resource.Success<ArrayList<Article>>) resource).getData();
                    articleAdapter.setArticleList(articles);
                    articleAdapter.notifyDataSetChanged();
                } else if (resource instanceof Resource.Error) {
                    String errorMessage = ((Resource.Error<ArrayList<Article>>) resource).getMessage();
                    Log.e("SearchError", errorMessage);
                } else if (resource instanceof Resource.Loading) {
                }
            }
        });

        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            String searchText = edtSearch.getText().toString().trim();

            if (!TextUtils.isEmpty(searchText)) {
                if (!searchHistory.contains(searchText)) {
                    searchHistory.add(0, searchText);

                    adapter.notifyDataSetChanged();
                    saveSearchHistory(searchHistory);
                }
                articleViewModel.searchArticles(searchText);
            } else {
                articleViewModel.searchArticles("");
            }
            return true;
        });

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedSearchText = searchHistory.get(position);

            // Cập nhật EditText
            edtSearch.setText(selectedSearchText);
            articleViewModel.searchArticles(selectedSearchText);
        });

        articleViewModel.loadArticlesItem();

        articleViewModel.loadArticlesItem();

        Button btnWorkout = view.findViewById(R.id.btnWorkout);
        btnWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_articlesearch_to_workoutSearch);
            }
        });

        Button btnAll = view.findViewById(R.id.btnAll);
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_articlesearch_to_allSearchFragment);
            }
        });

        return view;
    }

    private void saveSearchHistory(List<String> history) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("history", new HashSet<>(history));
        editor.apply();
    }

    private List<String> loadSearchHistory() {
        Set<String> historySet = sharedPreferences.getStringSet("history", new HashSet<>());
        return new ArrayList<>(historySet);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem itemsSearch = menu.findItem(R.id.ic_search);
        if (itemsSearch != null) {
            itemsSearch.setVisible(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Search");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        articleAdapter.setOnItemClickListener(article -> {
            Bundle bundle = new Bundle();
            bundle.putString("articleTitle", article.getArticle_title());
            navController.navigate(R.id.action_articlesearch_to_articleDetailFragment2, bundle);
        });
    }
}
