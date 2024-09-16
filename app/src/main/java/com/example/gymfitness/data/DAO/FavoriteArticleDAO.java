package com.example.gymfitness.data.DAO;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.gymfitness.data.entities.FavoriteArticle;

import java.util.List;

@Dao
public interface FavoriteArticleDAO {
    @Query("SELECT * FROM FavoriteArticle")
    List<FavoriteArticle> getAll();
}
