package com.example.river.localstorage

interface IDataHandler {
    fun insert(input:String, cb:CallBack)
    fun select(key:String, cb:CallBack)

    interface CallBack{
        fun onSuccess(info:String)
        fun onFailure(ex:String)
    }
}