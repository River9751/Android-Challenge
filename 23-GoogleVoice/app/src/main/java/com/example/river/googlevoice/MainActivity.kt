package com.example.river.googlevoice

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val requestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            resetColor()

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening...")
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-TW")
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)

            startActivityForResult(intent, requestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == requestCode && resultCode == Activity.RESULT_OK) {
            val matches = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val text = matches.first()
            textView.text = text
            changeColor(text)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun changeColor(text: String) {
        when {
            text.contains("三角形") -> triangle.setColorFilter(Color.YELLOW)
            text.contains("圓形") -> circle.setColorFilter(Color.YELLOW)
            text.contains("正方形") -> square.setColorFilter(Color.YELLOW)
            else -> resetColor()
        }
    }

    fun resetColor() {
        triangle.setColorFilter(Color.BLACK)
        circle.setColorFilter(Color.BLACK)
        square.setColorFilter(Color.BLACK)
    }
}
