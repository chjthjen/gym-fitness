package com.example.gymfitness.viewmodels;

import android.content.Context;

import androidx.databinding.BindingAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gymfitness.adapters.OnboardingVpAdapter;
import com.zhpan.indicator.IndicatorView;

import java.util.Objects;

public class OnBoardViewmodel {
    Context context;

    public static ViewPager2 getVp() {
        return vp;
    }

    public static void setVp(ViewPager2 vp) {
        OnBoardViewmodel.vp = vp;
    }

    static ViewPager2 vp;

    public OnboardingVpAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(OnboardingVpAdapter adapter) {
        this.adapter = adapter;
    }

    OnboardingVpAdapter adapter;
    public OnBoardViewmodel(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @BindingAdapter("setAdapter")
    public static void bindViewPager(ViewPager2 viewPager2, FragmentStateAdapter adapter) {
        vp = viewPager2;
        viewPager2.setAdapter(adapter);
        viewPager2.setUserInputEnabled(false);
    }

    @BindingAdapter("setIndicator")
    public static void bindIndicator(IndicatorView indicatorView, ViewPager2 viewPager2) {
        indicatorView.setPageSize(Objects.requireNonNull(viewPager2.getAdapter()).getItemCount());
        indicatorView.notifyDataChanged();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicatorView.setCurrentPosition(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                indicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
    }
}
