package com.example.gymfitness.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.NotificationWorkoutRCVAdapter;
import com.example.gymfitness.data.Entities.Notification;
import com.example.gymfitness.databinding.FragmentNotificationsWorkoutRemindersBinding;
import com.example.gymfitness.databinding.FragmentNotificationsWorkoutSystemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationsWorkoutSystemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsWorkoutSystemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private NotificationWorkoutRCVAdapter todayNotificationWorkoutRCVAdapter;
    private NotificationWorkoutRCVAdapter yesterdayNotificationWorkoutRCVAdapter;

    public NotificationsWorkoutSystemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationsWorkoutSystemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationsWorkoutSystemFragment newInstance(String param1, String param2) {
        NotificationsWorkoutSystemFragment fragment = new NotificationsWorkoutSystemFragment();
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
    private FragmentNotificationsWorkoutSystemBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationsWorkoutSystemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //rcv today
        todayNotificationWorkoutRCVAdapter = new NotificationWorkoutRCVAdapter(getContext());
        LinearLayoutManager todayLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        binding.rcvTodayNotificationWorkoutSystem.setLayoutManager(todayLinearLayoutManager);
        todayNotificationWorkoutRCVAdapter.setData(getListTodayNotificaionWorkout());
        binding.rcvTodayNotificationWorkoutSystem.setAdapter(todayNotificationWorkoutRCVAdapter);

        //rcv yesterday
        yesterdayNotificationWorkoutRCVAdapter = new NotificationWorkoutRCVAdapter(getContext());
        LinearLayoutManager yesterdayLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        binding.rcvTodayNotificationWorkoutSystem.setLayoutManager(yesterdayLinearLayoutManager);
        yesterdayNotificationWorkoutRCVAdapter.setData(getListYesterdayNotificaionWorkout());
        binding.rcvTodayNotificationWorkoutSystem.setAdapter(yesterdayNotificationWorkoutRCVAdapter);
    }
    private List<Notification> getListYesterdayNotificaionWorkout() {
        // Create some example data
        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(new Notification(R.drawable.list_off, "We've updated our privacy policy", "yesterday", "June 09 - 1:00 PM"));
        notificationList.add(new Notification(R.drawable.star_big_star_off, "You have a new message!", "yesterday", "June 09 - 9:00 AM"));

        return notificationList;
    }

    private List<Notification> getListTodayNotificaionWorkout() {
        // Create some example data
        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(new Notification(R.drawable.star_big_star_on, "You have a new message!", "today", "June 10 - 2:00 PM"));
        notificationList.add(new Notification(R.drawable.list_on, "scheduled maintenance.", "today", "June 10 - 8:00 AM"));
        notificationList.add(new Notification(R.drawable.notification_off_circle, "We've detected a login  from a new device", "today", "June 10 - 5:00 AM"));

        return notificationList;
    }
}