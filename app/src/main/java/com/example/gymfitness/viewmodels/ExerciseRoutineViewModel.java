package com.example.gymfitness.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.gymfitness.R;
import com.example.gymfitness.data.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseRoutineViewModel extends ViewModel {
    private List<Exercise> exercises1;
    private List<Exercise> exercises2;

    public ExerciseRoutineViewModel(List<Exercise> exercises1, List<Exercise> exercises2) {
        this.exercises1 = exercises1;
        this.exercises2 = exercises2;
    }
    public ExerciseRoutineViewModel(){
        this.exercises1=new ArrayList<>();
        this.exercises2=new ArrayList<>();

        List<Exercise> exercises1=new ArrayList<>();
        List<Exercise> exercises2=new ArrayList<>();
        exercises1.add(new Exercise(R.drawable.ic_round,"Kettlebell swing","00:30","repetition 3x"));
        exercises1.add(new Exercise(R.drawable.ic_round,"Kettlebell swing","00:30","repetition 3x"));
        exercises1.add(new Exercise(R.drawable.ic_round,"Kettlebell swing","00:30","repetition 3x"));
        exercises1.add(new Exercise(R.drawable.ic_round,"Kettlebell swing","00:30","repetition 3x"));
        exercises2.add(new Exercise(R.drawable.ic_round,"Kettlebell swing","00:30","repetition 3x"));
        exercises2.add(new Exercise(R.drawable.ic_round,"Kettlebell swing","00:30","repetition 3x"));
        this.exercises1=exercises1;
        this.exercises2=exercises2;
    }


    public List<Exercise> getExercises1() {
        return exercises1;
    }

    public void setExercises1(List<Exercise> exercises1) {
        this.exercises1 = exercises1;
    }

    public List<Exercise> getExercises2() {
        return exercises2;
    }

    public void setExercises2(List<Exercise> exercises2) {
        this.exercises2 = exercises2;
    }
}
