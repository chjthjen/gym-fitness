package com.example.gymfitness.adapters;

import android.animation.ValueAnimator;
import android.app.Notification;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;

import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.FAQViewHolder> {

    private List<String> titles;
    private List<String> contents;
    private Context context;

    public FAQAdapter(Context context, List<String> titles, List<String> contents) {
        this.context = context;
        this.titles = titles;
        this.contents = contents;
    }

    @NonNull
    @Override
    public FAQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faq_item, parent, false);
        return new FAQViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQViewHolder holder, int position) {
        String title = titles.get(position);
        String content = contents.get(position);

        holder.tvContent.setVisibility(View.GONE);
        holder.bottomDivider.setVisibility(View.GONE);

        holder.headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tvContent.getVisibility() == View.GONE) {
                    expandContent(holder.tvContent);
                    holder.bottomDivider.setVisibility(View.VISIBLE);
                } else {
                    collapseContent(holder.tvContent);
                    holder.bottomDivider.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public static class FAQViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent;
        LinearLayout headerLayout, bottomDivider;

        public FAQViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            headerLayout = itemView.findViewById(R.id.headerLayout);
            bottomDivider = itemView.findViewById(R.id.bottomDivider);
        }
    }

    private void expandContent(TextView tvContent) {
        tvContent.setVisibility(View.VISIBLE);

        tvContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tvContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int widthSpec = View.MeasureSpec.makeMeasureSpec(tvContent.getWidth(), View.MeasureSpec.EXACTLY);
                int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                tvContent.measure(widthSpec, heightSpec);

                int targetHeight = tvContent.getMeasuredHeight();

                Log.d("ExpandContent", "Measured height of TextView: " + targetHeight);

                ValueAnimator animator = ValueAnimator.ofInt(0, targetHeight);
                animator.addUpdateListener(animation -> {
                    int animatedValue = (int) animation.getAnimatedValue();
                    tvContent.getLayoutParams().height = animatedValue;
                    tvContent.requestLayout();
                    Log.d("ExpandContent", "Current animated height: " + animatedValue);
                });

                animator.setDuration(500);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.start();
            }
        });
    }




    private void collapseContent(TextView tvContent) {
        int initialHeight = tvContent.getMeasuredHeight();

        ValueAnimator animator = ValueAnimator.ofInt(initialHeight, 0);
        animator.addUpdateListener(animation -> {
            tvContent.getLayoutParams().height = (int) animation.getAnimatedValue();
            tvContent.requestLayout();
        });

        animator.setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();

        animator.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                tvContent.setVisibility(View.GONE);
                tvContent.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
        });
    }

}
