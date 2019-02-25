package com.bignerdranch.android.fitnesspal.util

import android.database.sqlite.SQLiteDatabase
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.AB_ROLLOUTS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.BACK_EXTENSION
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.BARBELL_BENCH_PRESS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.BARBELL_BENT_OVER_ROW
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.BARBELL_BICEPS_CURL
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.BARBELL_DEADLIFT
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.BARBELL_FRONT_RAISE
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.BARBELL_LUNGES
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.BARBELL_LYING_PULLOVER
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.BARBELL_MILITARY_PRESS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.BARBELL_SHRUGS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.BARBELL_SQUATS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.CHIN_UPS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.DUMPBELL_ARNOLD_PRESS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.DUMPBELL_BICEPS_CURL
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.DUMPBELL_CHEST_FLYE
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.DUMPBELL_CHEST_PULLOVER
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.DUMPBELL_FRONT_SQUAT
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.DUMPBELL_LATERAL_RAISE
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.DUMPBELL_LUNGES
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.DUMPBELL_ONE_ARM_ROW
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.DUMPBELL_OVERHEAD_PRESS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.HORIZONTAL_ROW
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.KILOGRAMS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.LEG_PRESS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.METERS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.PLANK
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.PULL_UPS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.SECONDS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.TIMES
import com.bignerdranch.android.fitnesspal.model.Exercise
import com.bignerdranch.android.fitnesspal.model.Measurement
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    var database: SQLiteDatabase? = null
    lateinit var measurementInKg: Measurement
    lateinit var measurementInMeters: Measurement
    lateinit var measurementInSecs: Measurement
    lateinit var measurementInTimes: Measurement

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    fun dateToMillis(date: String): Long {
        return dateFormat.parse(date).time
    }

    fun millisToDate(millis: Long): String {
        return dateFormat.format(Date(millis))
    }

    fun exerciseFromName(exerciseName: String, database: SQLiteDatabase) : Exercise {
        if (this.database == null) {
            this.database = database
            measurementInKg = Measurement.getMeasurementByName(database, KILOGRAMS)
            measurementInMeters = Measurement.getMeasurementByName(database, METERS)
            measurementInSecs = Measurement.getMeasurementByName(database, SECONDS)
            measurementInTimes = Measurement.getMeasurementByName(database, TIMES)
        }

        when (exerciseName) {
            BARBELL_SQUATS, BARBELL_DEADLIFT, BARBELL_LUNGES, BARBELL_BENCH_PRESS, BARBELL_LYING_PULLOVER,
            BARBELL_BENT_OVER_ROW, BARBELL_MILITARY_PRESS, BARBELL_FRONT_RAISE, BARBELL_SHRUGS, BARBELL_BICEPS_CURL,
            DUMPBELL_LATERAL_RAISE, DUMPBELL_LUNGES, DUMPBELL_OVERHEAD_PRESS, DUMPBELL_CHEST_FLYE,
            DUMPBELL_CHEST_PULLOVER, DUMPBELL_FRONT_SQUAT, DUMPBELL_ARNOLD_PRESS, DUMPBELL_BICEPS_CURL,
            DUMPBELL_ONE_ARM_ROW, BACK_EXTENSION, LEG_PRESS, HORIZONTAL_ROW -> return Exercise(exerciseName, measurementInKg)
            PULL_UPS, CHIN_UPS, AB_ROLLOUTS -> return Exercise(exerciseName,measurementInTimes)
            PLANK -> return Exercise(exerciseName,measurementInSecs)
            else -> return Exercise(exerciseName,measurementInMeters)
        }
    }
}
