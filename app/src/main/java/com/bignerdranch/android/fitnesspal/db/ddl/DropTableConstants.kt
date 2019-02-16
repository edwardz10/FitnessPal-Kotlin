package com.bignerdranch.android.fitnesspal.db.ddl

interface DropTableConstants {
    companion object {
        val DROP_TRAINING_SESSIONS_TABLE = "DROP TABLE training_sessions"

        val DROP_TRAINING_SESSION_TYPES_TABLE = "DROP TABLE training_session_types"

        val DROP_EXERCISE_SETS_TABLE = "DROP TABLE exercise_sets"

        val DROP_MEASUREMENTS_TABLE = "DROP TABLE measurements"

        val DROP_EXERCISE_TYPES_TABLE = "DROP TABLE exercise_types"

        val DROP_EXERCISES_TABLE = "DROP TABLE exercises"
    }
}
