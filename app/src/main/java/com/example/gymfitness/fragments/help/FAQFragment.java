package com.example.gymfitness.fragments.help;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.FAQAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FAQFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FAQFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FAQFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FAQFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FAQFragment newInstance(String param1, String param2) {
        FAQFragment fragment = new FAQFragment();
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
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.reV_FAQ);
        FAQAdapter adapter;

        List<String> titles = Arrays.asList(
                getString(R.string.lorem_ipsum_dolor_sit_amet),
                getString(R.string.lorem_ipsum_dolor_sit_amet),
                getString(R.string.lorem_ipsum_dolor_sit_amet),
                getString(R.string.lorem_ipsum_dolor_sit_amet),
                getString(R.string.lorem_ipsum_dolor_sit_amet),
                getString(R.string.lorem_ipsum_dolor_sit_amet));
        List<String> contents = Arrays.asList(
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_n_sed_do_eiusmod_tempor_incididunt_ut_labore_et_dolore_n_magna_aliqua),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_n_sed_do_eiusmod_tempor_incididunt_ut_labore_et_dolore_n_magna_aliqua),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_n_sed_do_eiusmod_tempor_incididunt_ut_labore_et_dolore_n_magna_aliqua),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_n_sed_do_eiusmod_tempor_incididunt_ut_labore_et_dolore_n_magna_aliqua),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_n_sed_do_eiusmod_tempor_incididunt_ut_labore_et_dolore_n_magna_aliqua),
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_n_sed_do_eiusmod_tempor_incididunt_ut_labore_et_dolore_n_magna_aliqua)
        );

        adapter = new FAQAdapter(getActivity(), titles, contents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}