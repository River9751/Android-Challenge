package com.example.river.ball.animators

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.*
import com.example.river.ball.R
import kotlinx.android.synthetic.main.interpolator.*

class InterpolatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.interpolator)

        linearInterpolator.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(interpolatorBall, "translationX", 0f, 500f, 0f)
            animator.interpolator = LinearInterpolator()
            animator.start()
        }

        accelerate.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(interpolatorBall, "translationX", 0f, 500f, 0f)
            animator.interpolator = AccelerateInterpolator()
            animator.start()
        }

        decelerate.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(interpolatorBall, "translationX", 0f, 500f, 0f)
            animator.interpolator = DecelerateInterpolator()
            animator.start()
        }

        accelerateDecelerate.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(interpolatorBall, "translationX", 0f, 500f, 0f)
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.start()
        }

        anticipate.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(interpolatorBall, "translationX", 0f, 500f, 0f)
            animator.interpolator = AnticipateInterpolator()
            animator.start()
        }

        overshoot.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(interpolatorBall, "translationX", 0f, 500f, 0f)
            animator.interpolator = OvershootInterpolator()
            animator.start()
        }

        anticipateOvershoot.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(interpolatorBall, "translationX", 0f, 500f, 0f)
            animator.interpolator = AnticipateOvershootInterpolator()
            animator.start()
        }

        bounce.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(interpolatorBall, "translationX", 0f, 500f, 0f)
            animator.interpolator = BounceInterpolator()
            animator.start()
        }

        cycle.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(interpolatorBall, "translationX", 0f, 500f, 0f)
            animator.interpolator = CycleInterpolator(5f)
            animator.start()
        }
    }
}