package com.example.gymfitness.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.CustomAdapterListViewWorkoutSearch;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutSearch_6_3_2_A#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutSearch_6_3_2_A extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listView;

    public WorkoutSearch_6_3_2_A() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutSearch_6_3_2_A.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutSearch_6_3_2_A newInstance(String param1, String param2) {
        WorkoutSearch_6_3_2_A fragment = new WorkoutSearch_6_3_2_A();
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
        View view = inflater.inflate(R.layout.fragment_workout_search_6_3_2__a, container, false);

        listView = view.findViewById(R.id.listView);

        String[] values = new String[] { "Circuit", "Split", "Challenge", "Legs", "Cardio" };

        CustomAdapterListViewWorkoutSearch adapter = new CustomAdapterListViewWorkoutSearch(getActivity(), values);
        listView.setAdapter(adapter);

        return view;
    }
}