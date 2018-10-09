package com.example.river.progresscontrol

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.SystemClock.sleep
import android.support.annotation.MainThread
import android.support.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var handler: Handler
    lateinit var runnable: Runnable
    var max = 100
    var now = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.max = this.max

        setUI()

        handler = Handler()

        runnable = Runnable {
            increaseProgress()
            handler.postDelayed(runnable, 500)
        }

        start.setOnClickListener {
            runnable.run()
        }

        pause.setOnClickListener {
            pause()
        }

        stop.setOnClickListener {
            pause()
            this.now = 0
            setUI()
        }
    }

    fun increaseProgress() {
        if (now == max) {
            pause()
            return
        }
        this.now += 10
        setUI()
//        progressBar.incrementProgressBy(10)
        println("*** $now")
    }

    fun pause() {
        handler.removeCallbacksAndMessages(null)
    }

    fun setUI(){
        progressBar.progress = now
        textView.text = "$now%"
    }

}
