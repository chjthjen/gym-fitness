package com.example.gymfitness.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "Article")
public class Article implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int article_id;

    private String article_title;
    private String article_content;
    private String article_thumbnail;
    private int is_favorite;
    private String publish_date;
    private String description;

    public Article(int article_id, String article_title, String article_thumbnail, String article_content, int is_favorite, String publish_date, String description) {
        this.article_id = article_id;
        this.article_title = article_title;
        this.article_thumbnail = article_thumbnail;
        this.article_content = article_content;
        this.is_favorite = is_favorite;
        this.publish_date = publish_date;
        this.description = description;
    }

    public Article() {
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_content() {
        return article_content;
    }

    public void setArticle_content(String article_content) {
        this.article_content = article_content;
    }

    public String getArticle_thumbnail() {
        return article_thumbnail;
    }

    public void setArticle_thumbnail(String article_thumbnail) {
        this.article_thumbnail = article_thumbnail;
    }

    public int getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
