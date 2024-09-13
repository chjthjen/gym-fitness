package com.example.gymfitness.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.CustomAdapterListViewWorkoutSearch;

import java.util.Objects;

public class WorkoutSearch_6_3_2_A extends Fragment {

    private ListView listView;

    public WorkoutSearch_6_3_2_A() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_search_6_3_2__a, container, false);

        listView = view.findViewById(R.id.listView);

        String[] values = new String[] { "Circuit", "Split", "Challenge", "Legs", "Cardio" };

        CustomAdapterListViewWorkoutSearch adapter = new CustomAdapterListViewWorkoutSearch(getActivity(), values);
        listView.setAdapter(adapter);

        return view;
    }

    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem itemsSearch = menu.findItem(R.id.ic_search);
        if(itemsSearch != null )
        {
            itemsSearch.setVisible(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Search");
    }
}