package com.bignerdranch.android.fitnesspal.model

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import java.util.*

class Measurement {
    var id: Long? = null
    private val name: String

    constructor(id: Long, name: String) {
        this.id = id
        this.name = name
    }

    constructor(name: String) {
        this.name = name
    }

    companion object {

        fun getContentValues(measurement: Measurement): ContentValues {
            val values = ContentValues()
            values.put("name", measurement.name)
            return values
        }

        fun getMeasurements(database: SQLiteDatabase): List<Measurement> {
            val measurements = LinkedList<Measurement>()
            val cursor = database.rawQuery("select * from measurements", null)

            try {
                cursor.moveToFirst()

                while (!cursor.isAfterLast) {
                    measurements.add(Measurement(cursor.getLong(0), cursor.getString(1)))
                    cursor.moveToNext()
                }
            } finally {
                cursor.close()
            }

            return measurements
        }

        fun getMeasurementByName(database: SQLiteDatabase, name: String): Measurement? {
            var measurement: Measurement? = null
            val query = "select * from measurements where name = '$name';"
            val cursor = database.rawQuery(query, null)

            try {
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    measurement = Measurement(cursor.getLong(0), cursor.getString(1))
                }
            } finally {
                cursor.close()
            }

            return measurement
        }
    }


}
