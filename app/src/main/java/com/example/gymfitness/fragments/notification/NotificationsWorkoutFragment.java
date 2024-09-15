package com.example.gymfitness.fragments.notification;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.NotificationsWorkoutVpAdapter;
import com.example.gymfitness.databinding.FragmentNotificationsWorkoutBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationsWorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsWorkoutFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public NotificationsWorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationsWorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationsWorkoutFragment newInstance(String param1, String param2) {
        NotificationsWorkoutFragment fragment = new NotificationsWorkoutFragment();
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

    private FragmentNotificationsWorkoutBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationsWorkoutBinding.inflate(inflater, container, false);

        NotificationsWorkoutVpAdapter adapter = new NotificationsWorkoutVpAdapter(this);
        binding.viewPager2NotificationsWorkout.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayoutNotificationsWorkout, binding.viewPager2NotificationsWorkout, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                // Tùy chỉnh từng tab
                View tabView = LayoutInflater.from(getContext()).inflate(R.layout.tab_notifications_workout_custom, null);
                TextView tabText = tabView.findViewById(R.id.tab_text);
                switch (position) {
                    case 0:
                        tabText.setText("Reminders");
                        break;
                    case 1:
                        tabText.setText("System");
                        break;
                }

                tab.setCustomView(tabView);
            }
        }).attach();

        // Cập nhật trạng thái khi tab được chọn
        binding.tabLayoutNotificationsWorkout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View tabView = tab.getCustomView();
                if (tabView != null) {
                    tabView.setSelected(true);  // Set trạng thái selected cho view
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View tabView = tab.getCustomView();
                if (tabView != null) {
                    tabView.setSelected(false);  // Hủy trạng thái selected
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return binding.getRoot();
    }
}