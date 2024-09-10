package com.example.gymfitness.data.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "UserInformation")
public class UserInformation implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String gender;
    private int age;
    private int weight;
    private int height;
    private String goal;
    private Date DoB;
    private String img;
    private String level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Date getDoB() {
        return DoB;
    }

    public void setDoB(Date doB) {
        DoB = doB;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
