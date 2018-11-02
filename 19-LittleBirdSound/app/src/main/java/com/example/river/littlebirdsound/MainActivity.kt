package com.example.river.littlebirdsound

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //MediaPlayer
    lateinit var cusMediaPlayer: CusMediaPlayer
    lateinit var runnable: Runnable
    lateinit var handler: Handler
    lateinit var animator: ObjectAnimator
    private var isSeeking = false

    //RecorderPlayer
    lateinit var recorderPlayer: RecorderPlayer
    private var isRecording = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        val a = A()
        a.C()
        A.B()

        buttonStart.setOnClickListener {
            buttonStart.text = cusMediaPlayer.playOrPauseMusic()

            when {
                animator.isPaused -> {
                    animator.resume()
                }
                animator.isRunning -> {
                    animator.pause()
                }
                else -> {
                    animator.start()
                }
            }
        }

        buttonStop.setOnClickListener {
            buttonStart.text = cusMediaPlayer.stopMusic()
            animator.end()
        }

        seekBarProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                isSeeking = false
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isSeeking = true
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (isSeeking) {
                    cusMediaPlayer.seekToProgress(progress)
                }
            }
        })

        seekBarVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                cusMediaPlayer.adjustVolume(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })


        startRecord.setOnClickListener {
            if (isRecording) {
                recorderPlayer.stopRecord()
                (it as Button).text = "Record"
                isRecording = false
            } else {
                recorderPlayer.startRecord()
                (it as Button).text = "Stop"
                isRecording = true
            }
        }

        playRecord.setOnClickListener { recorderPlayer.playRecord() }

        asyncProgressBar()
    }

    private fun init() {
        cusMediaPlayer = CusMediaPlayer(this, ::callBack)
        seekBarProgress.max = cusMediaPlayer.getDuration()

        seekBarVolume.progress = 50
        cusMediaPlayer.adjustVolume(50f)

        animator = ObjectAnimator.ofFloat(
            imageView,
            "rotationY",
            0.0f, 360.0f
        )
        animator.duration = 4000
        animator.repeatCount = ValueAnimator.INFINITE

        recorderPlayer = RecorderPlayer(this)
        recorderPlayer.checkPermission()
    }

    private fun asyncProgressBar() {
        handler = Handler()
        runnable = Runnable {
            seekBarProgress.progress = cusMediaPlayer.getCurrentPosition()
            handler.postDelayed(runnable, 500)
        }
        runnable.run()
    }

    private fun callBack() {
        cusMediaPlayer.stopMusic()
        seekBarProgress.progress = 0
        buttonStart.text = "Play"
        animator.end()
    }

}