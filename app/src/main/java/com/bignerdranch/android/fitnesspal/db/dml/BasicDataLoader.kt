package com.bignerdranch.android.fitnesspal.db.dml

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.AB_ROLLOUTS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.BACK_BICEPS
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
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.CHEST_TRICEPS
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
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.SHOULDERS_LEGS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.TIMES
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.TREADMILL
import com.bignerdranch.android.fitnesspal.model.*
import com.bignerdranch.android.fitnesspal.model.Set
import com.bignerdranch.android.fitnesspal.util.Utils
import java.util.*

class BasicDataLoader(database: SQLiteDatabase) : AbstractDataLoader(database) {

    public override val measurements: List<ContentValues>
        get() = Arrays.asList(
            Measurement.getContentValues(Measurement(KILOGRAMS)),
            Measurement.getContentValues(Measurement(METERS)),
            Measurement.getContentValues(Measurement(SECONDS)),
            Measurement.getContentValues(Measurement(TIMES))
        )

    public override val exercises: List<ContentValues>
        get() {
            val measurementInKg = Measurement.getMeasurementByName(database, KILOGRAMS)
            val measurementInMeters = Measurement.getMeasurementByName(database, METERS)
            val measurementInSecs = Measurement.getMeasurementByName(database, SECONDS)
            val measurementInTimes = Measurement.getMeasurementByName(database, TIMES)

            return Arrays.asList(
                Exercise.getContentValues(Exercise(BARBELL_SQUATS, measurementInKg!!)),
                Exercise.getContentValues(Exercise(BARBELL_DEADLIFT, measurementInKg)),
                Exercise.getContentValues(Exercise(BARBELL_LUNGES, measurementInKg)),
                Exercise.getContentValues(Exercise(BARBELL_BENCH_PRESS, measurementInKg)),
                Exercise.getContentValues(Exercise(BARBELL_LYING_PULLOVER, measurementInKg)),
                Exercise.getContentValues(Exercise(BARBELL_BENT_OVER_ROW, measurementInKg)),
                Exercise.getContentValues(Exercise(BARBELL_MILITARY_PRESS, measurementInKg)),
                Exercise.getContentValues(Exercise(BARBELL_FRONT_RAISE, measurementInKg)),
                Exercise.getContentValues(Exercise(BARBELL_SHRUGS, measurementInKg)),
                Exercise.getContentValues(Exercise(BARBELL_BICEPS_CURL, measurementInKg)),
                Exercise.getContentValues(Exercise(DUMPBELL_LATERAL_RAISE, measurementInKg)),
                Exercise.getContentValues(Exercise(DUMPBELL_LUNGES, measurementInKg)),
                Exercise.getContentValues(Exercise(DUMPBELL_OVERHEAD_PRESS, measurementInKg)),
                Exercise.getContentValues(Exercise(DUMPBELL_CHEST_FLYE, measurementInKg)),
                Exercise.getContentValues(Exercise(DUMPBELL_CHEST_PULLOVER, measurementInKg)),
                Exercise.getContentValues(Exercise(DUMPBELL_FRONT_SQUAT, measurementInKg)),
                Exercise.getContentValues(Exercise(DUMPBELL_ARNOLD_PRESS, measurementInKg)),
                Exercise.getContentValues(Exercise(DUMPBELL_BICEPS_CURL, measurementInKg)),
                Exercise.getContentValues(Exercise(DUMPBELL_ONE_ARM_ROW, measurementInKg)),
                Exercise.getContentValues(Exercise(BACK_EXTENSION, measurementInKg)),
                Exercise.getContentValues(Exercise(LEG_PRESS, measurementInKg)),
                Exercise.getContentValues(Exercise(HORIZONTAL_ROW, measurementInKg)),
                Exercise.getContentValues(Exercise(PULL_UPS, measurementInTimes!!)),
                Exercise.getContentValues(Exercise(CHIN_UPS, measurementInTimes)),
                Exercise.getContentValues(Exercise(PLANK, measurementInSecs!!)),
                Exercise.getContentValues(Exercise(AB_ROLLOUTS, measurementInTimes)),
                Exercise.getContentValues(Exercise(TREADMILL, measurementInMeters!!))
            )
        }

    public override val trainingSessionTypes: List<ContentValues>
        get() = Arrays.asList(
            TrainingSessionType.getContentValues(TrainingSessionType(CHEST_TRICEPS)),
            TrainingSessionType.getContentValues(TrainingSessionType(BACK_BICEPS)),
            TrainingSessionType.getContentValues(TrainingSessionType(SHOULDERS_LEGS))
        )

    public override val sets: List<ContentValues>
        get() {
            val backTrainingSessionType = TrainingSessionType.getTrainingSessionTypeByName(database, BACK_BICEPS)

            val treadMill = Exercise.getExerciseByName(database, TREADMILL)
            val pullUps = Exercise.getExerciseByName(database, PULL_UPS)
            val dumpbellOneArm = Exercise.getExerciseByName(database, DUMPBELL_ONE_ARM_ROW)
            val horizontalRow = Exercise.getExerciseByName(database, HORIZONTAL_ROW)
            val backExtension = Exercise.getExerciseByName(database, BACK_EXTENSION)
            val barbellBicepsCurl = Exercise.getExerciseByName(database, BARBELL_BICEPS_CURL)
            val dumpBellBicepsCurl = Exercise.getExerciseByName(database, DUMPBELL_BICEPS_CURL)
            val legPress = Exercise.getExerciseByName(database, LEG_PRESS)
            val plank = Exercise.getExerciseByName(database, PLANK)

            return Arrays.asList(
                Set.getContentValues(Set(1, backTrainingSessionType!!, treadMill!!)),
                Set.getContentValues(Set(3, backTrainingSessionType, pullUps!!)),
                Set.getContentValues(Set(3, backTrainingSessionType, dumpbellOneArm!!)),
                Set.getContentValues(Set(3, backTrainingSessionType, horizontalRow!!)),
                Set.getContentValues(Set(3, backTrainingSessionType, backExtension!!)),
                Set.getContentValues(Set(3, backTrainingSessionType, barbellBicepsCurl!!)),
                Set.getContentValues(Set(3, backTrainingSessionType, dumpBellBicepsCurl!!)),
                Set.getContentValues(Set(3, backTrainingSessionType, legPress!!)),
                Set.getContentValues(Set(1, backTrainingSessionType, plank!!))
            )
        }

    public override val trainingSessions: List<ContentValues>
        get() {
            val chestTrainingSessionType = TrainingSessionType.getTrainingSessionTypeByName(database, BACK_BICEPS)

            return Arrays.asList(
                TrainingSession.getContentValues(
                    TrainingSession(
                        Utils.dateToMillis("21.09.2018"),
                        chestTrainingSessionType!!
                    )
                ),
                TrainingSession.getContentValues(
                    TrainingSession(
                        Utils.dateToMillis("28.09.2018"),
                        chestTrainingSessionType
                    )
                ),
                TrainingSession.getContentValues(
                    TrainingSession(
                        Utils.dateToMillis("05.10.2018"),
                        chestTrainingSessionType
                    )
                )
            )
        }

    public override val reps: List<ContentValues>
        get() {
            val backTrainingSessionType = TrainingSessionType.getTrainingSessionTypeByName(database, BACK_BICEPS)
            val trainingSession =
                TrainingSession.getTrainingSessionsByTrainingSessionType(database, backTrainingSessionType!!)[0]

            val sets = Set.getSetsByTrainingSessionType(database, backTrainingSessionType)
            val treadmillSet = Set.pickSetByExerciseName(database, sets, TREADMILL)
            val pullUpSet = Set.pickSetByExerciseName(database, sets, PULL_UPS)
            val dumpbellOneArmSet = Set.pickSetByExerciseName(database, sets, DUMPBELL_ONE_ARM_ROW)
            val horizontalRowSet = Set.pickSetByExerciseName(database, sets, HORIZONTAL_ROW)
            val backExtensionSet = Set.pickSetByExerciseName(database, sets, BACK_EXTENSION)
            val dumpbellBicepsCurlSet = Set.pickSetByExerciseName(database, sets, DUMPBELL_BICEPS_CURL)
            val barbellBicepsCurlSet = Set.pickSetByExerciseName(database, sets, DUMPBELL_BICEPS_CURL)
            val legPressSet = Set.pickSetByExerciseName(database, sets, LEG_PRESS)
            val plankSet = Set.pickSetByExerciseName(database, sets, PLANK)

            return Arrays.asList(
                Rep.getContentValues(Rep(1, 1000.0f, treadmillSet!!, trainingSession)),

                Rep.getContentValues(Rep(6, 1.0f, pullUpSet!!, trainingSession)),
                Rep.getContentValues(Rep(4, 1.0f, pullUpSet, trainingSession)),
                Rep.getContentValues(Rep(4, 1.0f, pullUpSet, trainingSession)),

                Rep.getContentValues(Rep(10, 20.0f, dumpbellOneArmSet!!, trainingSession)),
                Rep.getContentValues(Rep(10, 20.0f, dumpbellOneArmSet, trainingSession)),
                Rep.getContentValues(Rep(8, 22.5f, dumpbellOneArmSet, trainingSession)),

                Rep.getContentValues(Rep(10, 5.0f, horizontalRowSet!!, trainingSession)),
                Rep.getContentValues(Rep(10, 5.0f, horizontalRowSet, trainingSession)),
                Rep.getContentValues(Rep(9, 5.0f, horizontalRowSet, trainingSession)),

                Rep.getContentValues(Rep(10, 15.0f, backExtensionSet!!, trainingSession)),
                Rep.getContentValues(Rep(10, 15.0f, backExtensionSet, trainingSession)),
                Rep.getContentValues(Rep(10, 15.0f, backExtensionSet, trainingSession)),

                Rep.getContentValues(Rep(10, 10.0f, dumpbellBicepsCurlSet!!, trainingSession)),
                Rep.getContentValues(Rep(8, 10.0f, dumpbellBicepsCurlSet, trainingSession)),
                Rep.getContentValues(Rep(8, 10.0f, dumpbellBicepsCurlSet, trainingSession)),

                Rep.getContentValues(Rep(6, 20.0f, barbellBicepsCurlSet!!, trainingSession)),
                Rep.getContentValues(Rep(8, 20.0f, barbellBicepsCurlSet, trainingSession)),
                Rep.getContentValues(Rep(6, 20.0f, barbellBicepsCurlSet, trainingSession)),

                Rep.getContentValues(Rep(10, 50.0f, legPressSet!!, trainingSession)),
                Rep.getContentValues(Rep(8, 100.0f, legPressSet, trainingSession)),
                Rep.getContentValues(Rep(6, 150.0f, legPressSet, trainingSession)),

                Rep.getContentValues(Rep(1, 60.0f, plankSet!!, trainingSession))
            )
        }
}
