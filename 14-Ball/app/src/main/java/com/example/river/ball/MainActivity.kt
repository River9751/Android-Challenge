package com.example.river.ball

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.river.ball.animators.AnimatorSetActivity
import com.example.river.ball.animators.InterpolatorActivity
import com.example.river.ball.animators.ObjectAnimatorActivity
import com.example.river.ball.animators.ValueAnimatorActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = getList()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CusAdapter(this, list, ::Click)
    }

    fun getList(): MutableList<AnimateItem> {
        val arryList =
            arrayListOf("ValueAnimator", "ObjectAnimator", "AnimatorSet", "Interpolator")
        val list = mutableListOf<AnimateItem>()
        for (i in 0 until arryList.size) {
            val item = AnimateItem(i, arryList[i])
            list.add(item)
        }
        return list
    }

    fun Click(position: Int) {
        var intent = Intent()

        when (position) {
            0 -> intent = Intent(this, ValueAnimatorActivity::class.java)
            1 -> intent = Intent(this, ObjectAnimatorActivity::class.java)
            2 -> intent = Intent(this, AnimatorSetActivity::class.java)
            3 -> intent = Intent(this, InterpolatorActivity::class.java)
        }

        startActivity(intent)

    }
}
