package com.example.gymfitness.fragments;

import android.graphics.Color;
import android.os.Bundle;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_progress_tracking,container,false);
        viewModel=new ViewModelProvider(requireActivity()).get(ProgressTrackingViewModel.class);

        List<DayOverview> dayOverviews=new ArrayList<>();
        viewModel.getData().observe(requireActivity(),days -> {
            dayOverviews.addAll(days);
            Log.d("KHANH", "Original list: " + dayOverviews.toString());
        });
        Log.d("KHANH",dayOverviews.toString());
        List<Integer> months = new ArrayList<>();
        for(DayOverview dayOverview:dayOverviews){
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate localDate=LocalDate.parse(dayOverview.getTime(),formatter);
            int month = localDate.getMonthValue();
            months.add(month);
        }
        List<BarEntry> fixedEntries = new ArrayList<>();
        for (int i = 0; i < dayOverviews.size(); i++){
            fixedEntries.add(new BarEntry(months.get(i).floatValue(), 170));
        }
//        fixedEntries.add(new BarEntry(0f, 170)); // Tháng 1
//        fixedEntries.add(new BarEntry(1f, 170)); // Tháng 2
//        fixedEntries.add(new BarEntry(2f, 170)); // Tháng 3
//        fixedEntries.add(new BarEntry(3f, 170)); // Tháng 4


        // Danh sách các giá trị thực của tháng
//        List<BarEntry> actualEntries = new ArrayList<>();
//        actualEntries.add(new BarEntry(0f, 169)); // Tháng 1
//        actualEntries.add(new BarEntry(1f, 156)); // Tháng 2
//        actualEntries.add(new BarEntry(2f, 165)); // Tháng 3
//        actualEntries.add(new BarEntry(3f, 159)); // Tháng 4
//        BarDataSet barDataSet=new BarDataSet(fixedEntries,"Steps");
//        barDataSet.setColor(Color.rgb(217,217,217));
//        barDataSet.setDrawValues(false);
//
//        BarDataSet actualBarDataSet = new BarDataSet(actualEntries,"hrehre" );
//        actualBarDataSet.setColor(Color.rgb(226,241,99));
//        actualBarDataSet.setDrawValues(false);
//
//        BarData barData=new BarData(barDataSet,actualBarDataSet);
//        barData.setBarWidth(0.3f);
//        binding.barChart.setData(barData);
//        binding.barChart.getDescription().setEnabled(false);
//        final String[] months1 = new String[]{"Jan", "Feb", "Mar", "Apr"};
//        XAxis xAxis=binding.barChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Đặt trục X ở dưới
//        xAxis.setDrawGridLines(false); // Ẩn các đường lưới dọc trục X
//        xAxis.setGranularity(1f); // Đặt khoảng cách giữa các giá trị là 1
//        xAxis.setGranularityEnabled(true);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(months1));
//        xAxis.setTextColor(Color.rgb(226,241,99));
//
//        YAxis yAxis=binding.barChart.getAxisLeft();
//        yAxis.setTextColor(Color.rgb(226,241,99));
//        yAxis.setDrawGridLines(false);
//
//        YAxis yAxisRight = binding.barChart.getAxisRight();
//        yAxisRight.setDrawLabels(false);
////        binding.barChart.setDrawBorders(true);
////        binding.barChart.setBorderColor(Color.rgb(217,217,217));
////        binding.barChart.setBorderWidth(1f);
//        binding.barChart.getLegend().setEnabled(false);
//        binding.barChart.invalidate();
//
//        List<DayOverview> dayOverviews1=new ArrayList<>();
////        dayOverviews1.add(new DayOverview(LocalDate.now(),"3,679","1hr40m"));
////        dayOverviews1.add(new DayOverview(LocalDate.now(),"3,679","1hr40m"));
////        dayOverviews1.add(new DayOverview(LocalDate.now(),"3,679","1hr40m"));
////        dayOverviews1.add(new DayOverview(LocalDate.now(),"3,679","1hr40m"));
////        dayOverviews1.add(new DayOverview(LocalDate.now(),"3,679","1hr40m"));
//        DayOverviewAdapter adapter=new DayOverviewAdapter(dayOverviews1);
//        binding.rvDayOverview.setAdapter(adapter);
//        binding.rvDayOverview.setLayoutManager(new LinearLayoutManager(getContext()));
//        binding.rvDayOverview.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.spacing)));
        return binding.getRoot();
    }
}