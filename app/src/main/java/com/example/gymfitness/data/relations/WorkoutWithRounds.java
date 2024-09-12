package com.example.gymfitness.data.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.gymfitness.data.Entities.Round;
import com.example.gymfitness.data.Entities.Workout;

import java.util.List;

public class WorkoutWithRounds {
    @Embedded
    public Workout workout;

    @Relation(
            parentColumn = "workout_id",
            entityColumn = "workout_id"
    )
    public List<Round> rounds;
}
