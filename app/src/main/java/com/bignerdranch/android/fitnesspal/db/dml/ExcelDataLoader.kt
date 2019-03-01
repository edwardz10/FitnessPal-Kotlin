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
import org.apache.poi.ss.formula.functions.NumericFunction.LOG
import org.apache.poi.ss.usermodel.Workbook
import java.lang.Exception
import java.util.*

class ExcelDataLoader(database: SQLiteDatabase) : AbstractDataLoader(database) {

    val FITNESS_REPORT_FILE = "fitness_report.xls"
    var workbook: Workbook

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
                BARBELL_SQUATS, BARBELL_DEADLIFT, BARBELL_LUNGES, BARBELL_BENCH_PRESS, BARBELL_LYING_PULLOVER,
                BARBELL_MILITARY_PRESS, BARBELL_FRONT_RAISE, BARBELL_SHRUGS, BARBELL_BICEPS_CURL,
                DUMPBELL_LATERAL_RAISE, DUMPBELL_LUNGES, DUMPBELL_OVERHEAD_PRESS, DUMPBELL_CHEST_FLYE,
                DUMPBELL_CHEST_PULLOVER, DUMPBELL_FRONT_SQUAT, DUMPBELL_ARNOLD_PRESS, DUMPBELL_BICEPS_CURL,
                DUMPBELL_ONE_ARM_ROW, BACK_EXTENSION, LEG_PRESS, HORIZONTAL_ROW, PULL_UPS, CHIN_UPS, PLANK,
                AB_ROLLOUTS, TREADMILL
            )
                .map { Exercise.getContentValues(Utils.exerciseFromName(it, database)) }
        }

    override val trainingSessionTypes: List<ContentValues> by lazy {
        val typesLocal = (0..workbook.numberOfSheets - 1)
            .toList()
            .map { TrainingSessionType.getContentValues(TrainingSessionType(workbook.getSheetAt(it).sheetName)) }

        typesLocal
    }

    override val sets: List<ContentValues> by lazy {
        val setsLocal = LinkedList<ContentValues>()

        (0 until workbook.numberOfSheets)
            .toList()
            .forEach {
                val sheet = workbook.getSheetAt(it)
                val trainingSessionType = sheet.sheetName
                val header = sheet.getRow(0)

                if (header != null) {
                    (header.firstCellNum..header.lastCellNum)
                        .toList()
                        .forEach {
                            val cell = header.getCell(it)

                            if (cell != null && !cell.stringCellValue.isEmpty()) {
                                Log.i(DbConstants.DATABASE_NAME, "Header: ${cell.stringCellValue}")
                            }

                        }
                }

            }

        setsLocal
    }

    override val reps: List<ContentValues>
        get() = emptyList()

    override val trainingSessions: List<ContentValues>
        get() = emptyList()


}