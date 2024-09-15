package com.example.gymfitness.data;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOverview {
    private LocalDateTime time;
    private String steps;
    private String duration;

    public DayOverview(LocalDateTime time, String steps, String duration) {
        this.time = time;
        this.steps = steps;
        this.duration = duration;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDayOfWeek(LocalDateTime time){
         return time.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }
    public int getDayOfMonth(LocalDateTime time){
        return time.getDayOfMonth();
    }
}
