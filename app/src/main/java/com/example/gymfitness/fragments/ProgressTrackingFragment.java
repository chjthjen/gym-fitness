package com.example.gymfitness.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.databinding.FragmentProgressTrackingBinding;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgressTrackingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressTrackingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentProgressTrackingBinding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProgressTrackingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgressTrackingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgressTrackingFragment newInstance(String param1, String param2) {
        ProgressTrackingFragment fragment = new ProgressTrackingFragment();
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
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_progress_tracking,container,false);

        List<BarEntry> fixedEntries = new ArrayList<>();
        fixedEntries.add(new BarEntry(0f, 170)); // Tháng 1
        fixedEntries.add(new BarEntry(1f, 170)); // Tháng 2
        fixedEntries.add(new BarEntry(2f, 170)); // Tháng 3
        fixedEntries.add(new BarEntry(3f, 170)); // Tháng 4

        // Danh sách các giá trị thực của tháng
        List<BarEntry> actualEntries = new ArrayList<>();
        actualEntries.add(new BarEntry(0f, 169)); // Tháng 1
        actualEntries.add(new BarEntry(1f, 156)); // Tháng 2
        actualEntries.add(new BarEntry(2f, 165)); // Tháng 3
        actualEntries.add(new BarEntry(3f, 159)); // Tháng 4
        BarDataSet barDataSet=new BarDataSet(fixedEntries,"Steps");
        barDataSet.setColor(Color.rgb(217,217,217));
        barDataSet.setDrawValues(false);

        BarDataSet actualBarDataSet = new BarDataSet(actualEntries,"hrehre" );
        actualBarDataSet.setColor(Color.rgb(226,241,99));
        actualBarDataSet.setDrawValues(false);

        BarData barData=new BarData(barDataSet,actualBarDataSet);
        barData.setBarWidth(0.3f);
        binding.barChart.setData(barData);
        binding.barChart.getDescription().setEnabled(false);
        final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr"};
        XAxis xAxis=binding.barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Đặt trục X ở dưới
        xAxis.setDrawGridLines(false); // Ẩn các đường lưới dọc trục X
        xAxis.setGranularity(1f); // Đặt khoảng cách giữa các giá trị là 1
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setTextColor(Color.rgb(226,241,99));

        YAxis yAxis=binding.barChart.getAxisLeft();
        yAxis.setTextColor(Color.rgb(226,241,99));
        yAxis.setDrawGridLines(false);

        YAxis yAxisRight = binding.barChart.getAxisRight();
        yAxisRight.setDrawLabels(false);
//        binding.barChart.setDrawBorders(true);
//        binding.barChart.setBorderColor(Color.rgb(217,217,217));
//        binding.barChart.setBorderWidth(1f);
        binding.barChart.getLegend().setEnabled(false);
        binding.barChart.invalidate();


        return binding.getRoot();
    }
}