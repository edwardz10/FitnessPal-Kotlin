package com.bignerdranch.android.fitnesspal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bignerdranch.android.fitnesspal.db.DbConstants
import com.bignerdranch.android.fitnesspal.db.FitnessPalDBHelper
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants
import com.bignerdranch.android.fitnesspal.model.*
import com.bignerdranch.android.fitnesspal.model.Set

class MainActivity : AppCompatActivity() {

    private lateinit var fitnessPalDBHelper: FitnessPalDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        fitnessPalDBHelper = FitnessPalDBHelper(this)

        for (measurement in Measurement.getMeasurements(fitnessPalDBHelper.getDatabase())) {
            Log.i(DbConstants.DATABASE_NAME, measurement.toString())
        }

        for (exercise in Exercise.getExercises(fitnessPalDBHelper.getDatabase())) {
            Log.i(DbConstants.DATABASE_NAME, exercise.toString())
        }

        for (trainingSessionType in TrainingSessionType.getTrainingSessionTypes(fitnessPalDBHelper.getDatabase())) {
            Log.i(DbConstants.DATABASE_NAME, trainingSessionType.toString())
        }

        val backBicepsSessionType =
            TrainingSessionType.getTrainingSessionTypeByName(fitnessPalDBHelper.getDatabase(),
                DataConstants.BACK_BICEPS
            )

        for (set in Set.getSetsByTrainingSessionType(
            fitnessPalDBHelper.getDatabase(),
            backBicepsSessionType)) {
            Log.i(DbConstants.DATABASE_NAME, set.toString())
        }

        val traningSessions = TrainingSession.getTrainingSessionsByTrainingSessionType(
            fitnessPalDBHelper.getDatabase(), backBicepsSessionType
        )

        for (trainingSession in traningSessions) {
            Log.i(DbConstants.DATABASE_NAME, trainingSession.toString())
        }

        val trainingSession = TrainingSession.pickSetByTrainingSessionTypeName(
            fitnessPalDBHelper.getDatabase(), traningSessions, DataConstants.BACK_BICEPS
        )

        val reps = Rep.getRepsByTrainingSession(fitnessPalDBHelper.getDatabase(), trainingSession)
        for (rep in reps) {
            Log.i(DbConstants.DATABASE_NAME, rep.toString())
        }
    }
}