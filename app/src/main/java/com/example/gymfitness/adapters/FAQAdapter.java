package com.example.gymfitness.adapters;

import android.animation.ValueAnimator;
import android.app.Notification;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        holder.linearContent.setVisibility(View.GONE);
        holder.bottomDivider.setVisibility(View.GONE);

        holder.headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.linearContent.getVisibility() == View.GONE) {
                    expandContent(holder.linearContent);
                    holder.bottomDivider.setVisibility(View.VISIBLE);
                } else {
                    collapseContent(holder.linearContent);
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
        LinearLayout linearContent, headerLayout, bottomDivider;

        public FAQViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            linearContent = itemView.findViewById(R.id.linear_content);
            headerLayout = itemView.findViewById(R.id.headerLayout);
            bottomDivider = itemView.findViewById(R.id.bottomDivider);
        }
    }

    private void expandContent(LinearLayout linearContent) {
        linearContent.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int targetHeight = linearContent.getMeasuredHeight();

        ValueAnimator animator = ValueAnimator.ofInt(0, targetHeight);
        animator.addUpdateListener(animation -> {
            linearContent.getLayoutParams().height = (int) animation.getAnimatedValue();
            linearContent.requestLayout();
        });

        animator.setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();

        linearContent.setVisibility(View.VISIBLE);
    }

    private void collapseContent(LinearLayout linearContent) {
        int initialHeight = linearContent.getMeasuredHeight();

        ValueAnimator animator = ValueAnimator.ofInt(initialHeight, 0);
        animator.addUpdateListener(animation -> {
            linearContent.getLayoutParams().height = (int) animation.getAnimatedValue();
            linearContent.requestLayout();
        });

        animator.setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();

        animator.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                linearContent.setVisibility(View.GONE);
            }
        });
    }
}
