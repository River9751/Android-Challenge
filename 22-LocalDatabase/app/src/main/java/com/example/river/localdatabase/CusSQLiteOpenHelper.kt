package com.example.river.localdatabase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class CusSQLiteOpenHelper(ctx: Context, val callBack: () -> Unit) : SQLiteOpenHelper(
    ctx,
    "test.db",
    null,
    1
) {

    val tableName = "User"

    override fun onCreate(db: SQLiteDatabase?) {
        val sql =
            "CREATE TABLE IF NOT EXISTS $tableName ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun addUser(userName: String) {
        try {
            val values = ContentValues()
            values.put("name", userName)
            writableDatabase.insert(tableName, null, values)
            callBack.invoke()
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    fun getUsers(): MutableList<ItemModel> {

        val cursor =
            readableDatabase.rawQuery("SELECT * FROM $tableName", null)

        val users = mutableListOf<ItemModel>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                users.add(ItemModel(id, name))
            } while (cursor.moveToNext())
        }

        cursor.close()

        return users
    }
}