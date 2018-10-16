package com.example.river.ball.animators

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.river.ball.R
import kotlinx.android.synthetic.main.value_animator.*

class ValueAnimatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.value_animator)

        title = "ValueAnimator"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val animator = ValueAnimator.ofFloat(0f, -350f, 0f)
        animator.duration = 500
        animator.addUpdateListener {
            valueBall.translationY = it.animatedValue as Float
        }

        valueBall.setOnClickListener {
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