package com.bignerdranch.android.fitnesspal.model

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import java.util.*

class Rep {

    var id: Long? = null
    private val times: Int?
    private val measurementNumber: Float?
    private val setId: Long?
    private val trainingSessionId: Long?

    constructor(
        id: Long?,
        times: Int?,
        measurementNumber: Float?,
        setId: Long?,
        trainingSessionId: Long?
    ) {
        this.id = id
        this.times = times
        this.measurementNumber = measurementNumber
        this.setId = setId
        this.trainingSessionId = trainingSessionId
    }

    constructor(
        times: Int?,
        measurementNumber: Float?,
        set: Set,
        trainingSession: TrainingSession
    ) : this(times, measurementNumber, set.id, trainingSession.id)

    constructor(
        times: Int?,
        measurementNumber: Float?,
        setId: Long?,
        trainingSessionId: Long?
    ) {
        this.times = times
        this.measurementNumber = measurementNumber
        this.setId = setId
        this.trainingSessionId = trainingSessionId
    }

    override fun toString(): String {
        return ("Rep(id=" + id
                + ", times=" + times
                + ", measurementNumber=" + measurementNumber
                + ", setId=" + setId
                + ", trainingSessionId=" + trainingSessionId + ")")
    }

    companion object {

        fun getContentValues(rep: Rep): ContentValues {
            val values = ContentValues()
            values.put("times", rep.times)
            values.put("measurement_number", rep.measurementNumber)
            values.put("set_id", rep.setId)
            values.put("training_session_id", rep.trainingSessionId)
            return values
        }


        fun getRepsByTrainingSession(
            database: SQLiteDatabase,
            trainingSession: TrainingSession?
        ): List<Rep> {
            val reps = LinkedList<Rep>()

            trainingSession?.let {
                val cursor = database.rawQuery(
                    "select * from reps where training_session_id=" + trainingSession.id!!, null
                )

                try {
                    cursor.moveToFirst()

                    val idColumn = cursor.getColumnIndex("_id")
                    val timesColumn = cursor.getColumnIndex("times")
                    val measurementNumber = cursor.getColumnIndex("measurement_number")
                    val setIdColumn = cursor.getColumnIndex("set_id")
                    val trainingSessionTypeIdColumn = cursor.getColumnIndex("training_session_id")

                    while (!cursor.isAfterLast) {
                        reps.add(
                            Rep(
                                cursor.getLong(idColumn),
                                cursor.getInt(timesColumn),
                                cursor.getFloat(measurementNumber),
                                cursor.getLong(setIdColumn),
                                cursor.getLong(trainingSessionTypeIdColumn)
                            )
                        )
                        cursor.moveToNext()
                    }
                } finally {
                    cursor.close()
                }
            }

            return reps
        }

        fun getAllReps(database: SQLiteDatabase): List<Rep> {
            val reps = LinkedList<Rep>()
            val cursor = database.rawQuery("select * from reps", null)

            try {
                cursor.moveToFirst()

                val idColumn = cursor.getColumnIndex("_id")
                val timesColumn = cursor.getColumnIndex("times")
                val measurementNumber = cursor.getColumnIndex("measurement_number")
                val setIdColumn = cursor.getColumnIndex("set_id")
                val trainingSessionTypeIdColumn = cursor.getColumnIndex("training_session_id")

                while (!cursor.isAfterLast) {
                    reps.add(
                        Rep(
                            cursor.getLong(idColumn),
                            cursor.getInt(timesColumn),
                            cursor.getFloat(measurementNumber),
                            cursor.getLong(setIdColumn),
                            cursor.getLong(trainingSessionTypeIdColumn)
                        )
                    )
                    cursor.moveToNext()
                }
            } finally {
                cursor.close()
            }

            return reps
        }
    }
}
