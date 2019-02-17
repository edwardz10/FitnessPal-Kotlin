package com.bignerdranch.android.fitnesspal.db.dml

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.bignerdranch.android.fitnesspal.db.ddl.CreateTableConstants

abstract class AbstractDataLoader(var database: SQLiteDatabase) : DataLoader {

    protected abstract val measurements: List<ContentValues>
    protected abstract val exercises: List<ContentValues>
    protected abstract val trainingSessionTypes: List<ContentValues>
    protected abstract val sets: List<ContentValues>
    protected abstract val trainingSessions: List<ContentValues>
    protected abstract val reps: List<ContentValues>

    override fun ddl() {
        this.database.execSQL(CreateTableConstants.CREATE_MEASUREMENTS_TABLE)
        this.database.execSQL(CreateTableConstants.CREATE_EXERCISES_TABLE)
        this.database.execSQL(CreateTableConstants.CREATE_TRAINING_SESSION_TYPES_TABLE)
        this.database.execSQL(CreateTableConstants.CREATE_TRAINING_SESSIONS_TABLE)
        this.database.execSQL(CreateTableConstants.CREATE_SETS_TABLE)
        this.database.execSQL(CreateTableConstants.CREATE_REPS_TABLE)
    }

    override fun dml() {
        for (contentValue in measurements) {
            database.insert("measurements", null, contentValue)
        }

        for (contentValue in exercises) {
            database.insert("exercises", null, contentValue)
        }

        for (contentValue in trainingSessionTypes) {
            database.insert("training_session_types", null, contentValue)
        }

        for (contentValue in sets) {
            database.insert("sets", null, contentValue)
        }

        for (contentValue in trainingSessions) {
            database.insert("training_sessions", null, contentValue)
        }

        for (contentValue in reps) {
            database.insert("reps", null, contentValue)
        }
    }
}
