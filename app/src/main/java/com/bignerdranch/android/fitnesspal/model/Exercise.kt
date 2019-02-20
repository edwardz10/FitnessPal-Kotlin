package com.bignerdranch.android.fitnesspal.model

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.bignerdranch.android.fitnesspal.util.Utils
import java.util.*

class Exercise {
    var id: Long? = null
    private val name: String
    private val measurementId: Long?

    constructor(id: Long, name: String, measurementId: Long) {
        this.id = id
        this.name = name
        this.measurementId = measurementId
    }

    constructor(name: String, measurement: Measurement) {
        this.name = name
        this.measurementId = measurement.id
    }

    override fun toString(): String {
        return ("Exercise(id=" + id
                + ", name=" + name
                + ", measurementId=" + measurementId + ")")
    }

    companion object {

        fun getContentValues(exercise: Exercise): ContentValues {
            val values = ContentValues()
            values.put("name", exercise.name)
            values.put("measurement_id", exercise.measurementId)
            return values
        }

        fun getExercises(database: SQLiteDatabase): List<Exercise> {
            val exercises = LinkedList<Exercise>()
            val cursor = database.rawQuery("select * from exercises", null)

            try {
                cursor.moveToFirst()

                while (!cursor.isAfterLast) {
                    exercises.add(
                        Exercise(
                            cursor.getLong(0),
                            cursor.getString(1),
                            cursor.getLong(2)
                        )
                    )
                    cursor.moveToNext()
                }
            } finally {
                cursor.close()
            }

            return exercises
        }

        fun getExerciseByName(database: SQLiteDatabase, name: String): Exercise? {
            var exercise: Exercise? = null
            val query = "select * from exercises where name = '$name';"
            val cursor = database.rawQuery(query, null)

            try {
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    exercise = Exercise(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getLong(2)
                    )
                }
            } finally {
                cursor.close()
            }

            return exercise
        }

        fun getExerciseById(database: SQLiteDatabase, id: Long?): Exercise? {
            var exercise: Exercise? = null
            val query = "select * from exercises where _id = '$id';"
            val cursor = database.rawQuery(query, null)

            try {
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    exercise = Exercise(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getLong(2)
                    )
                }
            } finally {
                cursor.close()
            }

            return exercise
        }
    }

}
