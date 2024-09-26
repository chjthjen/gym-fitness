package com.example.gymfitness.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "Notification")
public class Notification implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String type;
    private String content;
    private Date date;

    public Notification() {
    }

    public Notification(int id, String name, String type, String content, Date date) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.content = content;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
