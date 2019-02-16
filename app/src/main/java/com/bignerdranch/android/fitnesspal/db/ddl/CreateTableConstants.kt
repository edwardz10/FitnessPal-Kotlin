package com.bignerdranch.android.fitnesspal.db.ddl

interface CreateTableConstants {
    companion object {

        val CREATE_MEASUREMENTS_TABLE = "CREATE TABLE measurements(_id integer primary key, name)"

        val CREATE_EXERCISES_TABLE = (
                "CREATE TABLE exercises(_id integer primary key , "
                        + "name text, "
                        + "measurement_id integer references measurements(_id))")

        val CREATE_TRAINING_SESSION_TYPES_TABLE =
            "CREATE TABLE training_session_types(_id integer primary key , name text)"

        val CREATE_TRAINING_SESSIONS_TABLE = (
                "CREATE TABLE training_sessions(_id integer primary key , "
                        + "date integer,"
                        + "training_session_type_id integer references training_session_types(_id))")

        val CREATE_SETS_TABLE = (
                "CREATE TABLE sets(_id integer primary key , "
                        + "count integer,"
                        + "exercise_id integer references exercises(_id), "
                        + "training_session_type_id integer references training_session_types(_id))")

        val CREATE_REPS_TABLE = (
                "CREATE TABLE reps(_id integer primary key, "
                        + "times integer, "
                        + "measurement_number real, "
                        + "set_id integer references sets(_id), "
                        + "training_session_id integer references training_sessions(_id))")
    }

}