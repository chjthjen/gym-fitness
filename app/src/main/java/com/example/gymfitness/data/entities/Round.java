package com.example.gymfitness.data.entities;

import java.io.Serializable;
import java.util.ArrayList;


public class Round implements Serializable {
    private int round_id;
    private String round_name;
    private ArrayList<Exercise> exercises;


    public Round()
    {

    }

    public Round(int round_id, String round_name, ArrayList<Exercise> exercises) {
        this.round_id = round_id;
        this.round_name = round_name;
        this.exercises = exercises;
    }

    public int getRound_id() {
        return round_id;
    }

    public void setRound_id(int round_id) {
        this.round_id = round_id;
    }

    public String getRound_name() {
        return round_name;
    }

    public void setRound_name(String round_name) {
        this.round_name = round_name;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }
}
