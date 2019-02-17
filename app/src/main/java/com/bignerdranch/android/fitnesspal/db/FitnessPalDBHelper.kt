package com.bignerdranch.android.fitnesspal.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.bignerdranch.android.fitnesspal.db.DbConstants.Companion.DATABASE_NAME
import com.bignerdranch.android.fitnesspal.db.dml.BasicDataLoader
import com.bignerdranch.android.fitnesspal.db.dml.DataLoader

class FitnessPalDBHelper(context: Context) : SQLiteOpenHelper (context, DATABASE_NAME, null, VERSION) {

    private lateinit var database: SQLiteDatabase
    private lateinit var dataLoader: DataLoader

    private @Volatile var initialized: Boolean = false

    override fun onCreate(db: SQLiteDatabase) {
        this.database = db
        dataLoader = BasicDataLoader(this.database)

        dataLoader.ddl()
        dataLoader.dml()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    public fun getDatabase(): SQLiteDatabase {
        if (!initialized) {
            initialized = true
            database = writableDatabase
        }

        return database
    }

    companion object {
        private val VERSION = 1
    }
}
