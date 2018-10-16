package com.example.river.ball.animators

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.river.ball.R
import kotlinx.android.synthetic.main.animator_set.*

class AnimatorSetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animator_set)

        title = "AnimatorSet"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val animator1 = ObjectAnimator.ofFloat(
            setBall01,
            "translationY",
            0f, -500f, 0f
        )
        val animator2 = ObjectAnimator.ofFloat(
            setBall02,
            "translationY",
            0f, 500f, 0f
        )

        val animatorSet_onebyone = AnimatorSet()
        animatorSet_onebyone.duration = 500

        oneByOne.setOnClickListener {

            if (!animatorSet_onebyone.isRunning) {

                animatorSet_onebyone.play(animator1).after(animator2)
                animatorSet_onebyone.start()
            }
        }

        val animatorSet_sameTime = AnimatorSet()
        animatorSet_sameTime.duration = 500

        sameTime.setOnClickListener {
            if (!animatorSet_sameTime.isStarted) {
                animatorSet_sameTime.playTogether(animator1, animator2)
                animatorSet_sameTime.start()
            }
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