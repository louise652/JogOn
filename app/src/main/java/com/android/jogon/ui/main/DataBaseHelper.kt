package com.android.jogon.ui.main

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.android.jogon.ui.main.models.Run

const val DB_NAME = "RUN DB"
const val TBL_NAME = "Runs"
const val COL_DT = "Date"
const val COL_HOUR = "Hour"
const val COL_MIN = "Min"
const val COL_SEC = "Sec"
const val COL_DIST = "Distance"
const val COL_UNIT = "Unit"
const val COL_ID = "id"

/**
 * Class handles read/write to sql lite db
 */
class DataBaseHandler(private var context: Context) : SQLiteOpenHelper(
    context, DB_NAME, null,
    1
) {

    companion object {
        @Volatile
        private var INSTANCE: DataBaseHandler? = null

        @Synchronized
        fun getInstance(context: Context): DataBaseHandler = INSTANCE ?: DataBaseHandler(context).also { INSTANCE = it }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TBL_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COL_DT VARCHAR(256),$COL_HOUR VARCHAR(256),$COL_MIN VARCHAR(256),$COL_SEC VARCHAR(256),$COL_DIST VARCHAR(256),$COL_UNIT VARCHAR(256)  )"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //onCreate(db);
    }

    fun insertData(run: Run) {


        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_DT, run.date)
        contentValues.put(COL_HOUR, run.hour)
        contentValues.put(COL_MIN, run.min)
        contentValues.put(COL_SEC, run.sec)
        contentValues.put(COL_DIST, run.distance)
        contentValues.put(COL_UNIT, run.unit)

        val result = database.insert(TBL_NAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Run added", Toast.LENGTH_SHORT).show()
        }
        database.close()
    }

    fun readData(): MutableList<Run> {
        val list: MutableList<Run> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TBL_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {

                val dt = result.getString(result.getColumnIndex(COL_DT))
                val hour = result.getString(result.getColumnIndex(COL_HOUR)).toInt()
                val min = result.getString(result.getColumnIndex(COL_MIN)).toInt()
                val sec = result.getString(result.getColumnIndex(COL_SEC)).toInt()
                val dist = result.getString(result.getColumnIndex(COL_DIST)).toDouble()
                val unit = result.getString(result.getColumnIndex(COL_UNIT))
                val run = Run(dt, hour, min, sec, dist, unit)

                list.add(run)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }
}