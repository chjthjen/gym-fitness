package com.example.gymfitness.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOverview {
//    private LocalDate time1;
    private String reps;
    private String duration;
    private String time;

    public DayOverview(){

    }
    public DayOverview(String timeString, String reps, String duration) {
        this.time = timeString;
        this.reps = reps;
        this.duration = duration;
    }
//    public DayOverview(LocalDate time, String reps, String duration) {
//        this.time1 = time;
//        this.reps = reps;
//        this.duration = duration;
////        this.time=LocalDate.parse(time.toString(), DateTimeFormatter.ISO_LOCAL_DATE);
//    }

    public String getTime() {
        return time;
    }

    public void setTime(String timeString) {
        this.time = timeString;
    }


//    public LocalDate getTime() {
//        return time;
//    }
//
//    public void setTime(LocalDate time) {
//        this.time = time;
//    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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
