package com.devis.salarycounter.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.devis.salarycounter.model.UserMdl

/**
 * Created by devis on 2019-11-09
 */

class DatabaseHelper(
    context: Context,
    name: String? = "dbEmployee",
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = 1
) : SQLiteOpenHelper(context, name, factory, version) {

    companion object {
        const val COLUMN_GROUP = "employee_group"
        const val COLUMN_EMPLOYEE_ID = "employee_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_GENDER = "gender"
    }

    var dbName: String = ""
    val tableName: String = "employee"

    init {
        dbName = name!!
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val queryCreateTable =
            "CREATE TABLE IF NOT EXISTS $tableName ($COLUMN_EMPLOYEE_ID INTEGER PRIMARY KEY NOT NULL, " +
                    "$COLUMN_GROUP TEXT, $COLUMN_NAME TEXT, $COLUMN_PASSWORD TEXT, $COLUMN_GENDER TEXT)"
        db!!.execSQL(queryCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val queryDropTable = "DROP TABLE IF EXISTS $tableName"
        db!!.execSQL(queryDropTable)
        onCreate(db)
    }

    fun registerEmployee(userMdl: UserMdl) {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(COLUMN_EMPLOYEE_ID, userMdl.employeeId)
        cv.put(COLUMN_GROUP, userMdl.group)
        cv.put(COLUMN_NAME, userMdl.fullName)
        cv.put(COLUMN_PASSWORD, userMdl.password)
        cv.put(COLUMN_GENDER, userMdl.gender)
        db.insert(tableName, null, cv)
    }

    fun loginEmployee(employeeId: Int, password: String): UserMdl? {
        val db: SQLiteDatabase = this.readableDatabase
        val querySelectTable = "SELECT * FROM $tableName WHERE $COLUMN_EMPLOYEE_ID = '$employeeId' AND $COLUMN_PASSWORD = '$password'"
        val cursor: Cursor = db.rawQuery(querySelectTable, null)
        cursor.moveToFirst()

        val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
        val group = cursor.getString(cursor.getColumnIndex(COLUMN_GROUP))
        val gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER))

        return UserMdl(
            group = group,
            employeeId = employeeId,
            fullName = name,
            gender = gender)
    }

}