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
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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
        viewModel.loadProgressDayOverView1();


        LocalDate specificDate = LocalDate.now();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");
        String month = specificDate.format(monthFormatter);


        int day = specificDate.getDayOfMonth();
        String dayWithSuffix = getDayWithSuffix(day);
        String formattedDate = month + " " + dayWithSuffix;

        binding.tvDate.setText(formattedDate);
        viewModel.getData().observe(getViewLifecycleOwner(), dayOverviews1 -> {
            if (dayOverviews1 == null || dayOverviews1.isEmpty()) {
                Log.d("Fragment", "DayOverviews is empty or null");
                return;
            }
            Log.d("Fragment", "DayOverviews size: " + dayOverviews1.size());


            DayOverviewAdapter adapter=new DayOverviewAdapter(dayOverviews1);
            binding.rvDayOverview.setAdapter(adapter);
            binding.rvDayOverview.setLayoutManager(new LinearLayoutManager(getContext()));
//            binding.rvDayOverview.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.spacing)));
        });

        viewModel.getDataMonth().observe(getViewLifecycleOwner(),dayOverviews -> {
            if (dayOverviews == null || dayOverviews.isEmpty()) {
                Log.d("Fragment", "DayOverviews is empty or null");
                return;
            }
            Log.d("Fragment", "DayOverviews1 size: " + dayOverviews.size());
            List<BarEntry> fixedEntries = new ArrayList<>();
            List<BarEntry> actualEntries = new ArrayList<>();
//
//
            List<Integer> months = viewModel.getMonths(dayOverviews);
            List<Integer> reps = new ArrayList<>();


            for (DayOverview dayOverview : dayOverviews){
                reps.add(dayOverview.getRep());

                DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

                LocalDate localDate=LocalDate.parse(dayOverview.getDate()+ "-01",formatter);
                months.add(localDate.getMonthValue());

            }
            Log.d("Fragment", "months: " + months.size());
            Log.d("Fragment", "reps: " + reps.size());


            for (int i = 0; i < months.size(); i++) {
                fixedEntries.add(new BarEntry(months.get(i).floatValue()-1, 170));
                actualEntries.add(new BarEntry(months.get(i).floatValue()-1, reps.get(i).floatValue()));
            }

            final String[] months1 = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
            Log.d("Fragment", "fixedEntries size: " + fixedEntries.size());
            Log.d("Fragment", "actualEntries size: " + actualEntries.size());
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
            xAxis.setValueFormatter(new IndexAxisValueFormatter(months1));

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

        return binding.getRoot();
    }
    private String getDayWithSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return day + "th";  // 11th, 12th, 13th
        }
        switch (day % 10) {
            case 1:  return day + "st";
            case 2:  return day + "nd";
            case 3:  return day + "rd";
            default: return day + "th";
        }
    }
}