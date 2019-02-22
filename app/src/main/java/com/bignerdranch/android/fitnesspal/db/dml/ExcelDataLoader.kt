package com.bignerdranch.android.fitnesspal.db.dml

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.bignerdranch.android.fitnesspal.db.DbConstants

class ExcelDataLoader(database: SQLiteDatabase) : AbstractDataLoader(database) {

    init {
        Log.i(DbConstants.DATABASE_NAME, "Init block in ExcelDataLoader")
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