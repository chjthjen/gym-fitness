package com.example.gymfitness;

import java.sql.Date;

public class UserInfo {
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDoB() {
        return DoB;
    }

    public void setDoB(Date doB) {
        DoB = doB;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getGoal_id() {
        return goal_id;
    }

    public void setGoal_id(int goal_id) {
        this.goal_id = goal_id;
    }

    public int getLevel_id() {
        return level_id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }

    private String gender;
    private int weight;
    private int height;
    private int age;
    private Date DoB;
    private String user_image;
    private String user_id;
    private int goal_id;
    private int level_id;


    public UserInfo(String gender, int weight, int height, int age, Date doB, String user_image,String user_id,  int goal_id, int level_id) {
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.age = age;
        DoB = doB;
        this.user_image = user_image;
        this.user_id = user_id;
        this.goal_id = goal_id;
        this.level_id = level_id;
    }
}
