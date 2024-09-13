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
import com.example.gymfitness.data.entities.Notification;
import com.example.gymfitness.databinding.FragmentNotificationsWorkoutRemindersBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationsWorkoutRemindersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsWorkoutRemindersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private NotificationWorkoutRCVAdapter todayNotificationWorkoutRCVAdapter;
    private NotificationWorkoutRCVAdapter yesterdayNotificationWorkoutRCVAdapter;

    public NotificationsWorkoutRemindersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationsWorkoutRemindersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationsWorkoutRemindersFragment newInstance(String param1, String param2) {
        NotificationsWorkoutRemindersFragment fragment = new NotificationsWorkoutRemindersFragment();
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

    private FragmentNotificationsWorkoutRemindersBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationsWorkoutRemindersBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //rcv today
        todayNotificationWorkoutRCVAdapter = new NotificationWorkoutRCVAdapter(getContext());
        LinearLayoutManager todayLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        binding.rcvTodayNotificationWorkoutReminders.setLayoutManager(todayLinearLayoutManager);
        todayNotificationWorkoutRCVAdapter.setData(getListTodayNotificaionWorkout());
        binding.rcvTodayNotificationWorkoutReminders.setAdapter(todayNotificationWorkoutRCVAdapter);

        //rcv yesterday
        yesterdayNotificationWorkoutRCVAdapter = new NotificationWorkoutRCVAdapter(getContext());
        LinearLayoutManager yesterdayLinearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        binding.rcvYesterdayNotificationWorkoutReminders.setLayoutManager(yesterdayLinearLayoutManager);
        yesterdayNotificationWorkoutRCVAdapter.setData(getListYesterdayNotificaionWorkout());
        binding.rcvYesterdayNotificationWorkoutReminders.setAdapter(yesterdayNotificationWorkoutRCVAdapter);
    }

    private List<Notification> getListYesterdayNotificaionWorkout() {
        // Create some example data
        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(new Notification(R.drawable.cup_on, "Upper Body Workout Completed!", "yesterday", "June 09 - 6:00 pM"));
        notificationList.add(new Notification(R.drawable.bulb_off, "Remember Your Exercise Session", "yesterday", "June 09 - 3:00 PM"));
        notificationList.add(new Notification(R.drawable.list_off, "new Article & Tip posted!", "yesterday", "June 09 - 11:00 aM"));

        return notificationList;
    }

    private List<Notification> getListTodayNotificaionWorkout() {
        // Create some example data
        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(new Notification(R.drawable.star_big_star_off, "New Workout Is Available", "today", "June 10 - 10:00 AM"));
        notificationList.add(new Notification(R.drawable.bulb_on, "Dont't Forget To Drink Water", "today", "June 10 - 8:00 AM"));

        return notificationList;
    }
}