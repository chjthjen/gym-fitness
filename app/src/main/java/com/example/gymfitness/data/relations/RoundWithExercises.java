package com.example.gymfitness.data.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.gymfitness.data.Entities.Exercise;
import com.example.gymfitness.data.Entities.Round;

import java.util.List;

public class RoundWithExercises {
    @Embedded
    public Round round;

    @Relation(
            parentColumn = "round_id",
            entityColumn = "round_id"
    )
    public List<Exercise> exercises;
}
