package com.bignerdranch.android.fitnesspal.db.dml

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.bignerdranch.android.fitnesspal.MainActivity
import com.bignerdranch.android.fitnesspal.db.DbConstants
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook

class ExcelDataLoader(database: SQLiteDatabase) : AbstractDataLoader(database) {

    val FITNESS_REPORT_FILE = "fitness_report.xls"
    var workbook : Workbook

    init {
        Log.i(DbConstants.DATABASE_NAME, "Init block in ExcelDataLoader")
        val inputStream = MainActivity.context.assets.open(FITNESS_REPORT_FILE)
        workbook = HSSFWorkbook(inputStream)
        Log.i(DbConstants.DATABASE_NAME, FITNESS_REPORT_FILE + " successfully loaded")
    }

    override val trainingSessionTypes: List<ContentValues>
        get() = emptyList()

    override val sets: List<ContentValues>
        get() = emptyList()

    override val trainingSessions: List<ContentValues>
        get() = emptyList()

    override val reps: List<ContentValues>
        get() = emptyList()

    override val exercises: List<ContentValues>
        get() = emptyList()

    override val measurements: List<ContentValues>
        get() = emptyList()


}