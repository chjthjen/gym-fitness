package com.example.gymfitness.utils;

import android.app.AlertDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.adapters.notification.NotificationWorkoutRCVAdapter;
import com.example.gymfitness.adapters.favorites.FavoriteArticlesResourceAdapter;
import com.example.gymfitness.adapters.favorites.FavoriteWorkoutsAdapter;
import com.example.gymfitness.data.entities.Article;
import com.example.gymfitness.data.entities.Notification;
import com.example.gymfitness.data.entities.Workout;

public class SwipeToDeleteCallback<T extends RecyclerView.Adapter<?>> extends ItemTouchHelper.SimpleCallback {

    private final T mAdapter;
    private final OnItemDeletedListener mListener;
    private final Context mContext;

    public SwipeToDeleteCallback(T adapter, OnItemDeletedListener listener, Context context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        mListener = listener;
        mContext = context;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        new AlertDialog.Builder(mContext)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes", (dialog, which) -> {
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
                    } else if (mAdapter instanceof NotificationWorkoutRCVAdapter) {
                        Notification notification = ((NotificationWorkoutRCVAdapter) mAdapter).getNotificationAt(position);
                        if (notification != null) {
                            ((NotificationWorkoutRCVAdapter) mAdapter).removeItem(position);
                            mListener.onItemDeleted(notification);
                        }
                    } else {
                        throw new IllegalArgumentException("Unsupported adapter type: " + mAdapter.getClass().getSimpleName());
                    }
                })
                .setNegativeButton("No", (dialog, which) -> mAdapter.notifyItemChanged(position))
                .show();
    }

    public interface OnItemDeletedListener {
        void onItemDeleted(Object item);
    }
}