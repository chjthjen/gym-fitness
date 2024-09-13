package com.example.gymfitness.data.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Exercise",
        foreignKeys = @ForeignKey(entity = Round.class,
                parentColumns = "round_id",
                childColumns = "round_id"))
public class Exercise implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int exercise_id;
    private String exercise_name;
    private int rep;
    private String video_url;
    private String thumbnail;
    private int is_favorite;
    private String level;
    private int is_trained;
    private int round_id;

    public Exercise(int exercise_id, String exercise_name, int rep, String video_url, String thumbnail, int is_favorite, String level, int is_trained, int round_id) {
        this.exercise_id = exercise_id;
        this.exercise_name = exercise_name;
        this.rep = rep;
        this.video_url = video_url;
        this.thumbnail = thumbnail;
        this.is_favorite = is_favorite;
        this.level = level;
        this.is_trained = is_trained;
        this.round_id = round_id;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getExercise_name() {
        return exercise_name;
    }

    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getIs_trained() {
        return is_trained;
    }

    public void setIs_trained(int is_trained) {
        this.is_trained = is_trained;
    }

    public int getRound_id() {
        return round_id;
    }

    public void setRound_id(int round_id) {
        this.round_id = round_id;
    }
}
