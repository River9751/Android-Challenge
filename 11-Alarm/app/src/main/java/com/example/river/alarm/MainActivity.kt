package com.example.river.alarm

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendar = Calendar.getInstance()

        tvDate.setOnClickListener { dateClick() }
        tvTime.setOnClickListener { timeClick() }
        button.setOnClickListener { buttonClick() }
    }

    private fun dateClick() {
        DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        ).show()
    }

    private val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, date ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DATE, date)

        val d = SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN)
        tvDate.text = d.format(calendar.time)
    }

    private fun timeClick() {
        TimePickerDialog(
                this,
                timeSetListener,
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                true
        ).show()
    }

    private val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hour, minute ->

        calendar.set(Calendar.HOUR, hour)
        calendar.set(Calendar.MINUTE, minute)

        val t = SimpleDateFormat("HH:mm", Locale.TAIWAN)
        tvTime.text = t.format(calendar.time)
    }

    private fun buttonClick() {
        val time =
                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.TAIWAN)
                        .format(calendar.time)

        val builder = AlertDialog.Builder(this)

        builder.setPositiveButton("OK") { dialog, which  ->
            //
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            //
        }

        builder
                .setTitle("Title")
                .setMessage("$time")
                .create()
                .show()

    }
}