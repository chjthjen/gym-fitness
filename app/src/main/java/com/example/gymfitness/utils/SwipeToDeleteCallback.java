package com.example.gymfitness.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.adapters.favorites.FavoriteArticlesResourceAdapter;
import com.example.gymfitness.adapters.favorites.FavoriteWorkoutsAdapter;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.Workout;

public class SwipeToDeleteCallback<T extends RecyclerView.Adapter<?>> extends ItemTouchHelper.SimpleCallback {

    private final T mAdapter;
    private final OnItemDeletedListener mListener;

    public SwipeToDeleteCallback(T adapter, OnItemDeletedListener listener) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        mListener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if (mAdapter instanceof FavoriteArticlesResourceAdapter) {
            Article article = ((FavoriteArticlesResourceAdapter) mAdapter).getArticleAt(position);
            if (article != null) {
                ((FavoriteArticlesResourceAdapter) mAdapter).removeItem(position);
                mListener.onItemDeleted(article);
            }
        } else if (mAdapter instanceof FavoriteWorkoutsAdapter) {
            Workout workout = ((FavoriteWorkoutsAdapter) mAdapter).getWorkoutAt(position);
            if (workout != null) {
                ((FavoriteWorkoutsAdapter) mAdapter).removeItem(position);
                mListener.onItemDeleted(workout);
            }
        }
    }

    public interface OnItemDeletedListener {
        void onItemDeleted(Object item);
    }
}