package com.bignerdranch.android.fitnesspal.db.dml

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.bignerdranch.android.fitnesspal.MainActivity
import com.bignerdranch.android.fitnesspal.db.DbConstants
import com.bignerdranch.android.fitnesspal.model.Measurement
import com.bignerdranch.android.fitnesspal.model.TrainingSessionType
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

    override val exercises: List<ContentValues>
        get() = emptyList()

    override val reps: List<ContentValues>
        get() = emptyList()

    override val trainingSessions: List<ContentValues>
        get() = emptyList()


}