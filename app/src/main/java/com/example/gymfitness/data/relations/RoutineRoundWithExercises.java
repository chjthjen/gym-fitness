package com.example.gymfitness.data.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.gymfitness.data.entities.Exercise;
import com.example.gymfitness.data.entities.RoutineRound;
import com.example.gymfitness.data.entities.RoutineRoundExerciseCrossRef;

import java.util.List;

public class RoutineRoundWithExercises {
    @Embedded
    public RoutineRound routineRound;

    @Relation(
            parentColumn = "id",
            entityColumn = "exercise_id",
            associateBy = @Junction(RoutineRoundExerciseCrossRef.class)
    )

    public List<Exercise> exercises;
}
