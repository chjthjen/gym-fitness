package com.example.gymfitness.fragments.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gymfitness.R;
import com.example.gymfitness.adapters.help.FAQViewpagerAdapter;

public class FAQFragment extends Fragment {

    private ViewPager2 viewPager2;
    private FAQViewpagerAdapter adapter;
    private TextView buttonGeneral, buttonAccount, buttonServices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, container, false);

        viewPager2 = view.findViewById(R.id.viewPager);
        adapter = new FAQViewpagerAdapter(this);

        viewPager2.setAdapter(adapter);

        buttonGeneral = view.findViewById(R.id.tabTextView1);
        buttonAccount = view.findViewById(R.id.tabTextView2);
        buttonServices = view.findViewById(R.id.tabTextView3);

        buttonGeneral.setOnClickListener(v -> setCurrentTab(0));
        buttonAccount.setOnClickListener(v -> setCurrentTab(1));
        buttonServices.setOnClickListener(v -> setCurrentTab(2));

        setCurrentTab(0);

        return view;
    }

    private void setCurrentTab(int index) {
        viewPager2.setCurrentItem(index);
        updateTabStyle(index);
    }

    private void updateTabStyle(int selectedTabIndex) {
        buttonGeneral.setBackgroundResource(R.drawable.faq_type_no_selected);
        buttonAccount.setBackgroundResource(R.drawable.faq_type_no_selected);
        buttonServices.setBackgroundResource(R.drawable.faq_type_no_selected);

        switch (selectedTabIndex) {
            case 0:
                buttonGeneral.setBackgroundResource(R.drawable.faq_type_selected);
                break;
            case 1:
                buttonAccount.setBackgroundResource(R.drawable.faq_type_selected);
                break;
            case 2:
                buttonServices.setBackgroundResource(R.drawable.faq_type_selected);
                break;
        }
    }
}
