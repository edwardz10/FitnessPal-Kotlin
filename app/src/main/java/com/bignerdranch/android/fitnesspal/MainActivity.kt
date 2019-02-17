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

    private var fitnessPalDBHelper: FitnessPalDBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        fitnessPalDBHelper = FitnessPalDBHelper(this)

        for (measurement in Measurement.getMeasurements(fitnessPalDBHelper!!.readableDatabase)) {
            Log.i(DbConstants.DATABASE_NAME, measurement.toString())
        }

        for (exercise in Exercise.getExercises(fitnessPalDBHelper!!.readableDatabase)) {
            Log.i(DbConstants.DATABASE_NAME, exercise.toString())
        }

        for (trainingSessionType in TrainingSessionType.getTrainingSessionTypes(fitnessPalDBHelper!!.readableDatabase)) {
            Log.i(DbConstants.DATABASE_NAME, trainingSessionType.toString())
        }

        val backBicepsSessionType =
            TrainingSessionType.getTrainingSessionTypeByName(fitnessPalDBHelper!!.readableDatabase,
                DataConstants.BACK_BICEPS
            )

        for (set in Set.getSetsByTrainingSessionType(
            fitnessPalDBHelper!!.readableDatabase,
            backBicepsSessionType
        )) {
            Log.i(DbConstants.DATABASE_NAME, set.toString())
        }

        val traningSessions = TrainingSession.getTrainingSessionsByTrainingSessionType(
            fitnessPalDBHelper!!.readableDatabase, backBicepsSessionType!!
        )

        for (trainingSession in traningSessions) {
            Log.i(DbConstants.DATABASE_NAME, trainingSession.toString())
        }

        val trainingSession = TrainingSession.pickSetByTrainingSessionTypeName(
            fitnessPalDBHelper!!.readableDatabase, traningSessions, DataConstants.BACK_BICEPS
        )

        val reps = Rep.getRepsByTrainingSession(fitnessPalDBHelper!!.readableDatabase, trainingSession!!)
        for (rep in reps) {
            Log.i(DbConstants.DATABASE_NAME, rep.toString())
        }
    }
}