package com.example.river.ball.animators

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.river.ball.R
import kotlinx.android.synthetic.main.object_animator.*

class ObjectAnimatorActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.object_animator)

        title = "ObjectAnimator"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val animator = ObjectAnimator.ofFloat(
            objectBall,
            "rotationY",
            0.0f, 360.0f)
        animator.duration = 500

        objectBall.setOnClickListener{
            animator.start()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
        }

        return super.onOptionsItemSelected(item)
    }


}