package com.example.gymfitness.data.db;

public class Exercise {
    private int icon;
    private String title;
    private String time;
    private String roundedRepetition;

    public Exercise(String dumbbellRows, String time, String s, int ic_play_video) {
    }

    public Exercise(int icon, String title, String time, String roundedRepetition) {
        this.icon = icon;
        this.title = title;
        this.time = time;
        this.roundedRepetition = roundedRepetition;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoundedRepetition() {
        return roundedRepetition;
    }

    public void setRoundedRepetition(String roundedRepetition) {
        this.roundedRepetition = roundedRepetition;
    }
}
