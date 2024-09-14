package com.example.gymfitness.fragments.help;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FAQFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FAQViewpagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.viewPager);
        adapter = new FAQViewpagerAdapter(this);

        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            View customTab = LayoutInflater.from(getContext()).inflate(R.layout.faq_tab, null);
            TextView textView = customTab.findViewById(R.id.tabTextView);
            customTab.setBackgroundResource(R.drawable.faq_type_selected);
            switch (position) {
                case 0:
                    textView.setText("General");
                    break;
                case 1:
                    textView.setText("Account");
                    break;
                case 2:
                    textView.setText("Services");
                    break;
            }
            textView.setBackgroundResource(R.drawable.faq_type_no_selected);
            textView.setTextColor(getResources().getColor(R.color.lightpurple));
            tab.setCustomView(textView);
        }).attach();

        setInitialTabStyle();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateTabStyles();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabStyles();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Optionally handle reselection
            }
        });

        // Set tab spacing
        setTabSpacing(tabLayout);
        return view;
    }

    private void setInitialTabStyle() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                View customView = tab.getCustomView();
                if (customView != null) {
                    TextView textView = customView.findViewById(R.id.tabTextView);
                    if (i == 0) {
                        textView.setBackgroundResource(R.drawable.faq_type_selected);
                        textView.setTextColor(getResources().getColor(R.color.black));
                    } else {
                        textView.setBackgroundResource(R.drawable.faq_type_no_selected);
                        textView.setTextColor(getResources().getColor(R.color.lightpurple));
                    }
                }
            }
        }
    }

    private void updateTabStyles() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                View customView = tab.getCustomView();
                if (customView != null) {
                    TextView textView = customView.findViewById(R.id.tabTextView);
                    if (tab.isSelected()) {
                        textView.setBackgroundResource(R.drawable.faq_type_selected);
                        textView.setTextColor(getResources().getColor(R.color.black));
                    } else {
                        textView.setBackgroundResource(R.drawable.faq_type_no_selected);
                        textView.setTextColor(getResources().getColor(R.color.lightpurple));
                    }
                }
            }
        }
    }

    private void setTabSpacing(TabLayout tabLayout) {
        final int tabCount = tabLayout.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                View tabView = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
                if (tabView != null) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 1) {
                        params.setMargins(dpToPx(7), 0, dpToPx(7), 0);
                    } else {
                        params.setMargins(0, 0, 0, 0);
                    }
                    tabView.setLayoutParams(params);
                }
            }
        }
    }

    public int dpToPx(int dp) {
        Resources resources = getResources();
        return Math.round(dp * (resources.getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
