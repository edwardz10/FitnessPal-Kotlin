package com.bignerdranch.android.fitnesspal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bignerdranch.android.fitnesspal.db.DbConstants.Companion.DATABASE_NAME
import com.bignerdranch.android.fitnesspal.db.FitnessPalDBHelper
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.BACK_BICEPS
import com.bignerdranch.android.fitnesspal.model.*
import com.bignerdranch.android.fitnesspal.model.Set

class MainActivity : AppCompatActivity() {

    private var fitnessPalDBHelper: FitnessPalDBHelper? = null

    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fitnessPalDBHelper = FitnessPalDBHelper(this)

        for (measurement in Measurement.getMeasurements(fitnessPalDBHelper!!.getReadableDatabase())) {
            Log.i(DATABASE_NAME, measurement.toString())
        }

        for (exercise in Exercise.getExercises(fitnessPalDBHelper!!.getReadableDatabase())) {
            Log.i(DATABASE_NAME, exercise.toString())
        }

        for (trainingSessionType in TrainingSessionType.getTrainingSessionTypes(fitnessPalDBHelper!!.getReadableDatabase())) {
            Log.i(DATABASE_NAME, trainingSessionType.toString())
        }

        val backBicepsSessionType =
            TrainingSessionType.getTrainingSessionTypeByName(fitnessPalDBHelper!!.getReadableDatabase(), BACK_BICEPS)

        for (set in Set.getSetsByTrainingSessionType(
            fitnessPalDBHelper!!.getReadableDatabase(),
            backBicepsSessionType
        )) {
            Log.i(DATABASE_NAME, set.toString())
        }

        val traningSessions = TrainingSession.getTrainingSessionsByTrainingSessionType(
            fitnessPalDBHelper!!.getReadableDatabase(), backBicepsSessionType
        )

        for (trainingSession in traningSessions) {
            Log.i(DATABASE_NAME, trainingSession.toString())
        }

        val trainingSession = TrainingSession.pickSetByTrainingSessionTypeName(
            fitnessPalDBHelper!!.getReadableDatabase(), traningSessions, BACK_BICEPS
        )

        val reps = Rep.getRepsByTrainingSession(fitnessPalDBHelper!!.getReadableDatabase(), trainingSession)
        for (rep in reps) {
            Log.i(DATABASE_NAME, rep.toString())
        }
    }
}
