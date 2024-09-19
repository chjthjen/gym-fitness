package com.example.gymfitness.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOverview {
    private int rep;
    private int duration;
    private String date;

    public DayOverview(){

    }


    public DayOverview(String date, int duration, int rep) {
        this.date = date;
        this.duration = duration;
        this.rep = rep;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfWeek(String time){
        DateTimeFormatter  formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate localDate=LocalDate.parse(time,formatter);
        return localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }
    public int getDayOfMonth(String time){
        DateTimeFormatter  formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate localDate=LocalDate.parse(time,formatter);
        return localDate.getDayOfMonth();
    }
}
