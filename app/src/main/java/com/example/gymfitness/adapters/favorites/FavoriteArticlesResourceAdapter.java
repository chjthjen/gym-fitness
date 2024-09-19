package com.example.gymfitness.adapters.favorites;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gymfitness.R;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.databinding.RcvArticleItemBinding;
import com.example.gymfitness.helpers.FavoriteHelper;

import java.util.ArrayList;

public class FavoriteArticlesResourceAdapter extends RecyclerView.Adapter<FavoriteArticlesResourceAdapter.MyViewHolder> {
    private ArrayList<Article> list;
    private com.example.gymfitness.adapters.resources.ArticleResourceAdapter.OnItemClickListener listener;

    public FavoriteArticlesResourceAdapter(ArrayList<Article> list) {
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RcvArticleItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.rcv_article_item, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
        FavoriteHelper.checkFavorite(article, holder.itemView.getContext(), holder.binding.star);
        holder.binding.star.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                FavoriteHelper.setFavorite(article,v.getContext(), holder.binding.star);
                list.remove(article);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setArticleList(ArrayList<Article> articles) {
        this.list.clear();
        this.list.addAll(articles);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
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