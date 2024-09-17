package com.example.gymfitness.adapters.resources;

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
import com.example.gymfitness.databinding.RcvArticleItemBinding;

import java.util.ArrayList;

public class ArticleResourceAdapter extends RecyclerView.Adapter<com.example.gymfitness.adapters.resources.ArticleResourceAdapter.MyViewHolder> {
    private ArrayList<Article> list;
    private OnItemClickListener listener;

    public ArticleResourceAdapter(ArrayList<Article> list) {
        this.list = list;
    }

    public interface OnItemClickListener {
        void onItemClick(Article article);
    }

    public void setOnItemClickListener(com.example.gymfitness.adapters.resources.ArticleResourceAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public com.example.gymfitness.adapters.resources.ArticleResourceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RcvArticleItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.rcv_article_item, parent, false);
        return new com.example.gymfitness.adapters.resources.ArticleResourceAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.gymfitness.adapters.resources.ArticleResourceAdapter.MyViewHolder holder, int position) {
        Article article = list.get(position);
        holder.bind(article);

        Glide.with(holder.itemView.getContext())
                .load(article.getArticle_thumbnail())
                .into(holder.binding.thumbnail);

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
        private RcvArticleItemBinding binding;

        public MyViewHolder(RcvArticleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Article item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }
}