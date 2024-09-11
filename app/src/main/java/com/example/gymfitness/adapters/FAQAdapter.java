package com.example.gymfitness.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        holder.tvTitle.setText(title);
        holder.tvContent.setText(content);

        holder.headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tvContent.getVisibility() == View.GONE) {
                    holder.tvContent.setVisibility(View.VISIBLE);
                    rotateArrow(holder.arrowImage, 0f, 180f);
                } else {
                    holder.tvContent.setVisibility(View.GONE);
                    rotateArrow(holder.arrowImage, 180f, 0f);
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
        ImageView arrowImage;
        View headerLayout;

        public FAQViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            arrowImage = itemView.findViewById(R.id.arrowImage);
            headerLayout = itemView.findViewById(R.id.headerLayout);
        }
    }

    private void rotateArrow(ImageView arrow, float fromDegree, float toDegree) {
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(arrow, "rotation", fromDegree, toDegree);
        rotateAnimation.setDuration(300);
        rotateAnimation.start();
    }
}
