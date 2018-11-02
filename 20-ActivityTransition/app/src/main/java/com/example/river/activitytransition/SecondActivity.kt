package com.example.river.activitytransition

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val type:String? = intent.getStringExtra("type")
        if  (type != null){
            init(type)
        }
    }

    private fun init(type: String) {
        when (type) {
            "explode" -> {
                val explodeTransition = Explode()
                explodeTransition.duration = 2000
                window.enterTransition = explodeTransition
                window.exitTransition = explodeTransition
            }

            "slide" -> {
                val slideTransition = Slide()
                slideTransition.duration = 2000
                window.enterTransition = slideTransition
                window.exitTransition = slideTransition
            }

            "fade" -> {
                val fadeTransition = Fade()
                fadeTransition.duration = 2000
                window.enterTransition = fadeTransition
                window.exitTransition = fadeTransition
            }
        }

    }

}