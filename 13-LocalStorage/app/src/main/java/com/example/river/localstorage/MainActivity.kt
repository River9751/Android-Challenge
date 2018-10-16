package com.example.river.localstorage

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preference = Preference(this)

        save.setOnClickListener {
            preference.insert(editText.text.toString(), object : IDataHandler.CallBack {
                override fun onSuccess(info: String) {
                    Toast.makeText(
                        this@MainActivity, "name $info saved", Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(ex: String) {
                    Toast.makeText(
                        this@MainActivity, "Failed to save data", Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

        next.setOnClickListener {
            preference.select("name", object : IDataHandler.CallBack {
                override fun onSuccess(info: String) {
                    val intent = Intent(this@MainActivity, ResultActivity::class.java)
                    intent.putExtra("name", info)
                    startActivity(intent)
                }

                override fun onFailure(ex: String) {
                    Toast.makeText(
                        this@MainActivity, "Failed to read data $ex", Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

    }
}
