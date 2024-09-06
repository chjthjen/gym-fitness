package com.example.gymfitness.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Artical")
public class Artical implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int artical_id;

    private String artical_title;
    private String artical_content;
    private String artical_thumbnail;
    private boolean is_favorite;

    public Artical(int artical_id, String artical_title, String artical_content, String artical_thumbnail, boolean is_favorite) {
        this.artical_id = artical_id;
        this.artical_title = artical_title;
        this.artical_content = artical_content;
        this.artical_thumbnail = artical_thumbnail;
        this.is_favorite = is_favorite;
    }

    public int getArtical_id() {
        return artical_id;
    }

    public void setArtical_id(int artical_id) {
        this.artical_id = artical_id;
    }

    public String getArtical_title() {
        return artical_title;
    }

    public void setArtical_title(String artical_title) {
        this.artical_title = artical_title;
    }

    public String getArtical_content() {
        return artical_content;
    }

    public void setArtical_content(String artical_content) {
        this.artical_content = artical_content;
    }

    public String getArtical_thumbnail() {
        return artical_thumbnail;
    }

    public void setArtical_thumbnail(String artical_thumbnail) {
        this.artical_thumbnail = artical_thumbnail;
    }

    public boolean isIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(boolean is_favorite) {
        this.is_favorite = is_favorite;
    }
}
