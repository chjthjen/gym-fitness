package com.example.gymfitness.data;

import java.util.ArrayList;

public class WorkoutTest {

    private String name;
    private int duration;
    private int kcal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public WorkoutTest()
    {

    }
    public WorkoutTest(String name, int duration, int kcal) {
        this.name = name;
        this.duration = duration;
        this.kcal = kcal;
    }

    public static ArrayList<WorkoutTest> makeList()
    {
        ArrayList<WorkoutTest> list = new ArrayList<>();
        list.add(new WorkoutTest("Squat Exercise",12,120));
        list.add(new WorkoutTest("Full Body Stretching",12,120));
        return list;
    }

}

