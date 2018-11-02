package com.example.river.activitytransition

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        secondImage01.setOnClickListener {
            secondClick()
        }
        explode.setOnClickListener { startTransition("explode") }
        slide.setOnClickListener { startTransition("slide") }
        fade.setOnClickListener { startTransition("fade") }

    }

    private fun secondClick() {
        val intent = Intent(this, SecondActivity::class.java)
        val v1 = catImage01 as View
        val v2 = secondImage01 as View

        val p1 = android.util.Pair(v1, "catTrans")
        val p2 = android.util.Pair(v2, "secondTrans")
        val transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
            this, p1, p2
        )

        startActivity(intent, transitionActivityOptions.toBundle())
    }

    private fun startTransition(type: String) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("type", type)
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this)
        startActivity(
            intent,
            activityOptions.toBundle()
        )
    }
}