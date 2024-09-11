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
    private float weight;
    private int height;
    private String goal;
    private Date dob;
    private String img;
    private String level;
    private String fullname;
    private String nickname;
    private String email;
    private String phonenumber;

    public UserInformation(int id, String gender, int age, float weight, int height, String goal, Date dob, String img, String level, String fullname, String nickname, String email, String phonenumber) {
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.goal = goal;
        this.dob = dob;
        this.img = img;
        this.level = level;
        this.fullname = fullname;
        this.nickname = nickname;
        this.email = email;
        this.phonenumber = phonenumber;
    }

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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
