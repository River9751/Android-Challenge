package com.example.river.webviewsearch

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat.getSystemService
import android.view.inputmethod.InputMethodManager


class MainActivity : AppCompatActivity() {

    val URL = "https://google.com.tw"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webViewClient = WebViewClient()
        webView.webViewClient = webViewClient

        webView.loadUrl(URL)

        button.setOnClickListener {
            webView.loadUrl(URL + "/search?q=" + editText.text)
            hideSoftInput()
            //android:imeOptions="actionDone"
        }
    }

    fun hideSoftInput() {
        val imm =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}