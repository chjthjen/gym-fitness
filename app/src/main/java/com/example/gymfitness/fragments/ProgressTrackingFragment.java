package com.example.gymfitness.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.DayOverviewAdapter;
import com.example.gymfitness.data.DayOverview;
import com.example.gymfitness.databinding.FragmentProgressTrackingBinding;
import com.example.gymfitness.helpers.SpacesItemDecoration;
import com.example.gymfitness.viewmodels.ProgressTrackingViewModel;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgressTrackingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressTrackingFragment extends Fragment {

    private FragmentProgressTrackingBinding binding;
    private ProgressTrackingViewModel viewModel;
    public ProgressTrackingFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProgressTrackingFragment newInstance() {
        return new ProgressTrackingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_progress_tracking,container,false);
        viewModel=new ViewModelProvider(requireActivity()).get(ProgressTrackingViewModel.class);
        viewModel.loadProgressDayOverView();


        viewModel.getData().observe(getViewLifecycleOwner(), dayOverviews1 -> {
            if (dayOverviews1 == null || dayOverviews1.isEmpty()) {
                Log.d("Fragment", "DayOverviews is empty or null");
                return;
            }
            Log.d("Fragment", "DayOverviews size: " + dayOverviews1.size());
            List<BarEntry> fixedEntries = new ArrayList<>();
            List<BarEntry> actualEntries = new ArrayList<>();
            List<Integer> months = viewModel.getMonths(dayOverviews1);
            List<Integer> reps = new ArrayList<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (DayOverview dayOverview : dayOverviews1) {
                try {
                    LocalDate localDate = LocalDate.parse(String.valueOf(dayOverview.getDate()), formatter);
                    String yearMonth = localDate.getYear() + "-" + String.format("%02d", localDate.getMonthValue());
                    Integer rep = viewModel.calculateTotalsByMonth(dayOverviews1).get(yearMonth);
                    reps.add(rep != null ? rep : 0);
                } catch (DateTimeParseException e) {
                    Log.e("Fragment", "Date parsing error: " + e.getMessage());
                }
            }

            if (months.size() != reps.size()) {
                Log.e("Fragment", "Mismatch between months and reps size");
                return; // Stop processing if there's an error
            }

            for (int i = 0; i < months.size(); i++) {
                fixedEntries.add(new BarEntry(months.get(i).floatValue(), 170));
                actualEntries.add(new BarEntry(months.get(i).floatValue(), reps.get(i).floatValue()));
            }

            BarDataSet barDataSet = new BarDataSet(fixedEntries, "Steps");
            barDataSet.setColor(Color.rgb(217, 217, 217));
            barDataSet.setDrawValues(false);

            BarDataSet actualBarDataSet = new BarDataSet(actualEntries, "Steps");
            actualBarDataSet.setColor(Color.rgb(226, 241, 99));
            actualBarDataSet.setDrawValues(false);

            BarData barData = new BarData(barDataSet, actualBarDataSet);
            barData.setBarWidth(0.3f);
            binding.barChart.setData(barData);
            binding.barChart.getDescription().setEnabled(false);

            XAxis xAxis = binding.barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            xAxis.setTextColor(Color.rgb(226, 241, 99));

            YAxis yAxis = binding.barChart.getAxisLeft();
            yAxis.setTextColor(Color.rgb(226, 241, 99));
            yAxis.setDrawGridLines(false);

            YAxis yAxisRight = binding.barChart.getAxisRight();
            yAxisRight.setDrawLabels(false);
            binding.barChart.setDrawBorders(true);
            binding.barChart.setBorderColor(Color.rgb(217, 217, 217));
            binding.barChart.setBorderWidth(1f);
            binding.barChart.getLegend().setEnabled(false);
            binding.barChart.invalidate();
        });





//
//        List<DayOverview> dayOverviews1=new ArrayList<>();
////        dayOverviews1.add(new DayOverview(LocalDate.now(),"3,679","1hr40m"));
////        dayOverviews1.add(new DayOverview(LocalDate.now(),"3,679","1hr40m"));
////        dayOverviews1.add(new DayOverview(LocalDate.now(),"3,679","1hr40m"));
////        dayOverviews1.add(new DayOverview(LocalDate.now(),"3,679","1hr40m"));
////        dayOverviews1.add(new DayOverview(LocalDate.now(),"3,679","1hr40m"));
//        DayOverviewAdapter adapter=new DayOverviewAdapter(dayOverviews);
//        binding.rvDayOverview.setAdapter(adapter);
//        binding.rvDayOverview.setLayoutManager(new LinearLayoutManager(getContext()));
//        binding.rvDayOverview.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.spacing)));
        return binding.getRoot();
    }
}