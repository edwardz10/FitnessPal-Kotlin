package com.bignerdranch.android.fitnesspal.model

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import java.util.*

class Set {
    var id: Long? = null
    private val count: Int?
    private val trainingSessionTypeId: Long?
    private val exerciseId: Long?

    constructor(
        id: Long?,
        count: Int?,
        trainingSessionTypeId: Long?,
        exerciseId: Long?
    ) {
        this.id = id
        this.count = count
        this.trainingSessionTypeId = trainingSessionTypeId
        this.exerciseId = exerciseId
    }

    constructor(
        count: Int?,
        trainingSessionType: TrainingSessionType,
        exercise: Exercise
    ) : this(count, trainingSessionType.id, exercise.id)

    constructor(
        count: Int?,
        trainingSessionTypeId: Long?,
        exerciseId: Long?
    ) {
        this.count = count
        this.trainingSessionTypeId = trainingSessionTypeId
        this.exerciseId = exerciseId
    }

    override fun toString(): String {
        return ("Set(id=" + id
                + ", count=" + count
                + ", trainingSessionTypeId=" + trainingSessionTypeId
                + ", exerciseId=" + exerciseId + ")")
    }

    companion object {

        fun getContentValues(set: Set): ContentValues {
            val values = ContentValues()
            values.put("count", set.count)
            values.put("training_session_type_id", set.trainingSessionTypeId)
            values.put("exercise_id", set.exerciseId)
            return values
        }

        fun getSetsByTrainingSessionType(
            database: SQLiteDatabase,
            trainingSessionType: TrainingSessionType?
        ): List<Set> {
            val sets = LinkedList<Set>()
            val cursor = database.rawQuery(
                "select * from sets where training_session_type_id=" + trainingSessionType?.id!!, null
            )

            try {
                cursor.moveToFirst()

                val idColumn = cursor.getColumnIndex("_id")
                val countColumn = cursor.getColumnIndex("count")
                val exerciseIdColumn = cursor.getColumnIndex("exercise_id")
                val trainingSessionTypeIdColumn = cursor.getColumnIndex("training_session_type_id")

                while (!cursor.isAfterLast) {
                    sets.add(
                        Set(
                            cursor.getLong(idColumn),
                            cursor.getInt(countColumn),
                            cursor.getLong(trainingSessionTypeIdColumn),
                            cursor.getLong(exerciseIdColumn)
                        )
                    )
                    cursor.moveToNext()
                }
            } finally {
                cursor.close()
            }

            return sets
        }

        fun pickSetByExerciseName(
            database: SQLiteDatabase,
            sets: List<Set>,
            exerciseName: String
        ): Set? {
            var result: Set? = null
            val exercise = Exercise.getExerciseByName(database, exerciseName)

            for (set in sets) {
                if (set.exerciseId == exercise!!.id) {
                    result = set
                    break
                }
            }

            return result
        }
    }
}
