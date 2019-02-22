package com.bignerdranch.android.fitnesspal.model

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.bignerdranch.android.fitnesspal.util.Utils
import java.util.*

class TrainingSession {
    var id: Long? = null
    val date: Long?
    val trainingSessionTypeId: Long?

    constructor(
        id: Long?,
        date: Long?,
        trainingSessionTypeId: Long?
    ) {
        this.id = id
        this.date = date
        this.trainingSessionTypeId = trainingSessionTypeId
    }

    constructor(
        date: Long?,
        trainingSessionType: TrainingSessionType
    ) : this(date, trainingSessionType.id)

    constructor(
        date: Long?,
        trainingSessionTypeId: Long?
    ) {
        this.date = date
        this.trainingSessionTypeId = trainingSessionTypeId
    }

    override fun toString(): String {
        return ("TrainingSession(id=" + id
                + ", date=" + Utils.millisToDate(date)
                + ", trainingSessionTypeId=" + trainingSessionTypeId + ")")
    }

    companion object {

        fun getContentValues(trainingSession: TrainingSession): ContentValues {
            val values = ContentValues()
            values.put("date", trainingSession.date)
            values.put("training_session_type_id", trainingSession.trainingSessionTypeId)
            return values
        }

        fun getTrainingSessionsByTrainingSessionType(
            database: SQLiteDatabase,
            trainingSessionType: TrainingSessionType?
        ): List<TrainingSession> {
            val trainingSessions = LinkedList<TrainingSession>()

            trainingSessionType?.let {
                val cursor = database.rawQuery(
                    "select * from training_sessions where training_session_type_id=" + trainingSessionType.id!!, null
                )

                try {
                    cursor.moveToFirst()

                    val idColumn = cursor.getColumnIndex("_id")
                    val dateColumn = cursor.getColumnIndex("date")
                    val trainingSessionTypeIdColumn = cursor.getColumnIndex("training_session_type_id")

                    while (!cursor.isAfterLast) {
                        trainingSessions.add(
                            TrainingSession(
                                cursor.getLong(idColumn),
                                cursor.getLong(dateColumn),
                                cursor.getLong(trainingSessionTypeIdColumn)
                            )
                        )
                        cursor.moveToNext()
                    }
                } finally {
                    cursor.close()
                }
            }

            return trainingSessions
        }

        fun getTrainingSessionsByDate(
            database: SQLiteDatabase,
            date: Long?
        ): TrainingSession? {
            var trainingSession: TrainingSession? = null
            val cursor = database.rawQuery(
                "select * from training_sessions where date=" + date!!, null
            )

            try {
                cursor.moveToFirst()

                val idColumn = cursor.getColumnIndex("_id")
                val dateColumn = cursor.getColumnIndex("date")
                val trainingSessionTypeIdColumn = cursor.getColumnIndex("training_session_type_id")

                if (!cursor.isAfterLast) {
                    trainingSession = TrainingSession(
                        cursor.getLong(idColumn),
                        cursor.getLong(dateColumn),
                        cursor.getLong(trainingSessionTypeIdColumn)
                    )
                }
            } finally {
                cursor.close()
            }

            return trainingSession
        }

        fun pickSetByTrainingSessionTypeName(
            database: SQLiteDatabase,
            trainingSessions: List<TrainingSession>,
            trainingSessionTypeName: String
        ): TrainingSession? {
            var result: TrainingSession? = null
            val trainingSessionType =
                TrainingSessionType.getTrainingSessionTypeByName(database, trainingSessionTypeName)

            for (trainingSession in trainingSessions) {
                if (trainingSession.trainingSessionTypeId == trainingSessionType!!.id) {
                    result = trainingSession
                    break
                }
            }

            return result
        }
    }

}
