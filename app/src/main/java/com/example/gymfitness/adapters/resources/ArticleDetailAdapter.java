package com.example.gymfitness.adapters.resources;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.ArticleDetail;

import java.util.List;

public class ArticleDetailAdapter extends RecyclerView.Adapter<ArticleDetailAdapter.ViewHolder> {

    private List<ArticleDetail> articleDetails;

    public ArticleDetailAdapter(List<ArticleDetail> articleDetails) {
        this.articleDetails = articleDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_article_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArticleDetail articleDetail = articleDetails.get(position);
        holder.txtHeader.setText(articleDetail.getHeader());
        holder.txtContent.setText(articleDetail.getContent());
    }

    @Override
    public int getItemCount() {
        return articleDetails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtHeader;
        TextView txtContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHeader = itemView.findViewById(R.id.txtHeader);
            txtContent = itemView.findViewById(R.id.txtContent);
        }
    }

    public void setArticleDetails(List<ArticleDetail> articleDetails){
        this.articleDetails = articleDetails;
    }
}
