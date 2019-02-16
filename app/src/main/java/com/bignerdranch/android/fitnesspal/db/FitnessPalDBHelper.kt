package com.bignerdranch.android.fitnesspal.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.bignerdranch.android.fitnesspal.db.dml.BasicDataLoader
import com.bignerdranch.android.fitnesspal.db.dml.DataLoader

@Data
@EqualsAndHashCode(callSuper = false)
class FitnessPalDBHelper(context: Context) : SQLiteOpenHelper(context, Companion.getDATABASE_NAME(), null, VERSION) {

    private var database: SQLiteDatabase? = null
    private var dataLoader: DataLoader? = null

    override fun onCreate(db: SQLiteDatabase) {
        this.database = db
        dataLoader = BasicDataLoader(database)


        dataLoader!!.ddl()
        dataLoader!!.dml()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    private fun getDatabase(): SQLiteDatabase {
        if (database == null) {
            database = writableDatabase
        }

        return database
    }

    companion object {

        private val VERSION = 1
    }
}
