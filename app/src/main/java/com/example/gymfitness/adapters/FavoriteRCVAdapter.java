package com.example.gymfitness.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfitness.R;
import com.example.gymfitness.data.DAO.FavoriteArticleDAO;
import com.example.gymfitness.data.entities.FavoriteArticle;
import com.example.gymfitness.data.entities.FavoriteExercise;
import com.example.gymfitness.data.entities.FavoriteWorkout;

import java.util.List;
import java.util.Objects;

public class FavoriteRCVAdapter extends RecyclerView.Adapter<FavoriteRCVAdapter.FavoriteHolder> {

    private static final int TYPE_DATA1 = 0;
    private static final int TYPE_DATA2 = 1;
    private static final int TYPE_DATA3 = 2;

    List<FavoriteArticle> lsFavoriteArticles ;
    List<FavoriteExercise> lsFavoriteExercises;
    List<FavoriteWorkout> lsFavoriteWorkouts;
    List<Objects> lsFavorites;

    private void setData (List<Objects> lsAllFavorites) {
//        this.lsFavorites = lsAllFavorites;
//        notifyDataSetChanged();

    }
    public void setDataArticle (List<FavoriteArticle> lsArticles) {
        this.lsFavoriteArticles = lsArticles;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_article_item, parent, false);

//        if (TYPE_DATA3 == viewType) {
//        }
//        else {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_workout_item_video,parent,false);
//        }

        return new FavoriteHolder(view);
    }

//    @Override
//    public int getItemViewType(int position) {
//        FavoriteExercise favoriteExercise = new FavoriteExercise();
//        FavoriteArticle favoriteArticle = new FavoriteArticle();
//        FavoriteWorkout favoriteWorkout = new FavoriteWorkout();
//        if(lsFavorites.get(position) instanceof FavoriteArticle)
//            return 0;;
//            else if (lsFavorites.get(position) instanceof FavoriteExercise)
//        return super.getItemViewType(position);
//    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {
    FavoriteArticle favoriteArticle = lsFavoriteArticles.get(position);
    if(favoriteArticle == null)
        return;

    holder.tvFavoriteName.setText(favoriteArticle.getArticle_name());
    }

    @Override
    public int getItemCount() {
        if(lsFavoriteArticles != null){
            return  lsFavoriteArticles.size();
        }
        return 0;
    }

    public  class FavoriteHolder extends RecyclerView.ViewHolder {

        TextView tvFavoriteName;

        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);

            tvFavoriteName = (TextView) itemView.findViewById(R.id.workout_name);
        }
    }
}
