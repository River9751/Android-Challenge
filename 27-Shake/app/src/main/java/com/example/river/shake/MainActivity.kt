package com.example.river.shake

import android.app.Service
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.*
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sensorManager: SensorManager
    lateinit var vibrator: Vibrator
    lateinit var vibrationEffect: VibrationEffect
    lateinit var imageList: ArrayList<Int>
    lateinit var handler: Handler

    var current = 0
    var isChanging = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler = Handler()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        initImages()

        val sensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

            override fun onSensorChanged(event: SensorEvent?) {
                if (event != null) {
                    val x = Math.abs(event.values[0])
                    val y = Math.abs(event.values[1])
                    val z = Math.abs(event.values[2])

                    if (x > 15 || y > 15 || z > 15) {
                        changeImage()
                    }
                }
            }
        }
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        vibrator = getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
    }

    private fun initImages() {
        imageList = arrayListOf(
            R.drawable.img01,
            R.drawable.img02,
            R.drawable.img03,
            R.drawable.img04,
            R.drawable.img05,
            R.drawable.img06
        )
    }


    fun changeImage() {
        if (isChanging) {
            return
        }

        isChanging = true
        if (current != imageList.size) {
            imageView.setImageResource(imageList[current])
            current++
        } else {
            imageView.setImageResource(imageList[0])
            current = 0
        }

        if (Build.VERSION.SDK_INT >= 26) {
            vibrationEffect = VibrationEffect.createOneShot(1000, 150)
            vibrator.vibrate(vibrationEffect)
        }else{
            vibrator.vibrate(1000)
        }

        val runnable = Runnable {
            isChanging = false
        }

        isChanging = false
        handler.postDelayed(runnable, 1000)
    }
}
