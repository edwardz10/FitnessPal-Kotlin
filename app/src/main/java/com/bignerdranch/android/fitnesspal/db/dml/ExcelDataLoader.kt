package com.bignerdranch.android.fitnesspal.db.dml

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.bignerdranch.android.fitnesspal.MainActivity
import com.bignerdranch.android.fitnesspal.db.DbConstants
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.AB_ROLLOUTS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.BACK_EXTENSION
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.BARBELL_BENCH_PRESS
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
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.LEG_PRESS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.PLANK
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.PULL_UPS
import com.bignerdranch.android.fitnesspal.db.dml.DataConstants.TREADMILL
import com.bignerdranch.android.fitnesspal.model.Exercise
import com.bignerdranch.android.fitnesspal.model.Measurement
import com.bignerdranch.android.fitnesspal.model.TrainingSessionType
import com.bignerdranch.android.fitnesspal.util.Utils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook
import java.util.*

class ExcelDataLoader(database: SQLiteDatabase) : AbstractDataLoader(database) {

    val FITNESS_REPORT_FILE = "fitness_report.xls"
    var workbook : Workbook

    init {
        Log.i(DbConstants.DATABASE_NAME, "Init block in ExcelDataLoader")
        val inputStream = MainActivity.context.assets.open(FITNESS_REPORT_FILE)
        workbook = HSSFWorkbook(inputStream)
        Log.i(DbConstants.DATABASE_NAME, FITNESS_REPORT_FILE + " successfully loaded")
    }

    override val measurements: List<ContentValues> by lazy {
        Arrays.asList(
            Measurement.getContentValues(Measurement(DataConstants.KILOGRAMS)),
            Measurement.getContentValues(Measurement(DataConstants.METERS)),
            Measurement.getContentValues(Measurement(DataConstants.SECONDS)),
            Measurement.getContentValues(Measurement(DataConstants.TIMES))
        )
    }

    override val exercises: List<ContentValues>
        get() {
            return Arrays.asList(
                Exercise.getContentValues(Utils.exerciseFromName(BARBELL_SQUATS, database)),
                Exercise.getContentValues(Utils.exerciseFromName(BARBELL_DEADLIFT, database)),
                Exercise.getContentValues(Utils.exerciseFromName(BARBELL_LUNGES, database)),
                Exercise.getContentValues(Utils.exerciseFromName(BARBELL_BENCH_PRESS, database)),
                Exercise.getContentValues(Utils.exerciseFromName(BARBELL_LYING_PULLOVER, database)),
                Exercise.getContentValues(Utils.exerciseFromName(BARBELL_MILITARY_PRESS, database)),
                Exercise.getContentValues(Utils.exerciseFromName(BARBELL_FRONT_RAISE, database)),
                Exercise.getContentValues(Utils.exerciseFromName(BARBELL_SHRUGS, database)),
                Exercise.getContentValues(Utils.exerciseFromName(BARBELL_BICEPS_CURL, database)),
                Exercise.getContentValues(Utils.exerciseFromName(DUMPBELL_LATERAL_RAISE, database)),
                Exercise.getContentValues(Utils.exerciseFromName(DUMPBELL_LUNGES, database)),
                Exercise.getContentValues(Utils.exerciseFromName(DUMPBELL_OVERHEAD_PRESS, database)),
                Exercise.getContentValues(Utils.exerciseFromName(DUMPBELL_CHEST_FLYE, database)),
                Exercise.getContentValues(Utils.exerciseFromName(DUMPBELL_CHEST_PULLOVER, database)),
                Exercise.getContentValues(Utils.exerciseFromName(DUMPBELL_FRONT_SQUAT, database)),
                Exercise.getContentValues(Utils.exerciseFromName(DUMPBELL_ARNOLD_PRESS, database)),
                Exercise.getContentValues(Utils.exerciseFromName(DUMPBELL_BICEPS_CURL, database)),
                Exercise.getContentValues(Utils.exerciseFromName(DUMPBELL_ONE_ARM_ROW, database)),
                Exercise.getContentValues(Utils.exerciseFromName(BACK_EXTENSION, database)),
                Exercise.getContentValues(Utils.exerciseFromName(LEG_PRESS, database)),
                Exercise.getContentValues(Utils.exerciseFromName(HORIZONTAL_ROW, database)),
                Exercise.getContentValues(Utils.exerciseFromName(PULL_UPS, database)),
                Exercise.getContentValues(Utils.exerciseFromName(CHIN_UPS, database)),
                Exercise.getContentValues(Utils.exerciseFromName(PLANK, database)),
                Exercise.getContentValues(Utils.exerciseFromName(AB_ROLLOUTS, database)),
                Exercise.getContentValues(Utils.exerciseFromName(TREADMILL, database))
            )
        }

    override val trainingSessionTypes: List<ContentValues> by lazy {
        val typesLocal = LinkedList<ContentValues>()

        for (i in 0 .. workbook.numberOfSheets - 1) {
            typesLocal.add(TrainingSessionType.getContentValues(TrainingSessionType(workbook.getSheetAt(i).sheetName)))
        }

        typesLocal
    }

    override val sets: List<ContentValues> by lazy {
        val setsLocal = LinkedList<ContentValues>()
        setsLocal
    }

    override val reps: List<ContentValues>
        get() = emptyList()

    override val trainingSessions: List<ContentValues>
        get() = emptyList()


}