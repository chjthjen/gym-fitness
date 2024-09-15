package com.example.gymfitness.adapters.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.databinding.ArticlesTipsRvItemBinding;

import java.util.ArrayList;

public class ArticlesTipsRCVAdapter extends RecyclerView.Adapter<ArticlesTipsRCVAdapter.MyViewHolder> {
    private ArrayList<Article> list;
    private OnItemClickListener listener;

    public ArticlesTipsRCVAdapter(ArrayList<Article> list) {
        this.list = list;
    }

    public interface OnItemClickListener {
        void onItemClick(Article article);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArticlesTipsRCVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ArticlesTipsRvItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.articles_tips_rv_item, parent, false);
        return new ArticlesTipsRCVAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesTipsRCVAdapter.MyViewHolder holder, int position) {
        Article article = list.get(position);
        holder.bind(article);

        Glide.with(holder.itemView.getContext())
                .load(article.getArticle_thumbnail())
                .into(holder.binding.imgWomanHelp);

        holder.binding.executePendingBindings();

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(article);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setArticleList(ArrayList<Article> articles) {
        this.list.clear();
        this.list.addAll(articles);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ArticlesTipsRvItemBinding binding;

        public MyViewHolder(ArticlesTipsRvItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Article item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }
}