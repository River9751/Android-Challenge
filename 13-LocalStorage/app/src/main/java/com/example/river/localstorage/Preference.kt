package com.example.river.localstorage

import android.content.Context
import android.content.SharedPreferences
import java.lang.Exception

class Preference : IDataHandler {

    var preference: SharedPreferences

    constructor(context: Context) {
        preference =
                context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    }

    override fun insert(input: String, cb: IDataHandler.CallBack) {
        val editor = preference.edit()
        try {
            editor.putString("name", input).apply()
            cb.onSuccess(input)
        } catch (ex: Exception) {
            cb.onFailure(ex.message.toString())
        }
    }

    override fun select(key: String, cb: IDataHandler.CallBack) {
        try {
            val result = preference.getString(key, "Default")
            cb.onSuccess(result!!)
        } catch (ex: Exception) {
            cb.onFailure(ex.message.toString())
        }
    }

    fun selectAll(): MutableList<String> {
        var list = mutableListOf<String>()
        for (i in preference.all.keys) {
            val value = preference.getString(i, "Default")
            list.add(value!!)
        }
        return list
    }

}