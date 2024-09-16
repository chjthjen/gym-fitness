package com.example.gymfitness.data.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gymfitness.data.entities.FavoriteArticle;

import java.util.List;

@Dao
public interface FavoriteArticleDAO {
    @Query("SELECT * FROM FavoriteArticle")
    List<FavoriteArticle> getAll();

    @Query("DELETE FROM FavoriteArticle WHERE article_name = :article_name")
    void delete(String article_name);

    @Insert
    void insert(FavoriteArticle favoriteArticle);

    @Query("SELECT * FROM FavoriteArticle WHERE article_name = :article_name")
    FavoriteArticle getArticle(String article_name);
}
