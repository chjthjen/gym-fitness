package com.example.gymfitness.data.entities;

import androidx.room.Entity;

@Entity(primaryKeys = {"routineRoundId", "exerciseId"})
public class RoutineRoundExerciseCrossRef {
    public int routineRoundId;
    public int exerciseId;

    public RoutineRoundExerciseCrossRef(int routineRoundId, int exerciseId) {
        this.routineRoundId = routineRoundId;
        this.exerciseId = exerciseId;
    }
}
