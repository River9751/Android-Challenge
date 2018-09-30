package com.example.river.imagepicker

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val ACTION_CAMERA_REQUEST_CODE = 0
    val ACTION_ALBUM_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        album.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, ACTION_ALBUM_REQUEST_CODE)
        }

        camera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, ACTION_CAMERA_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            ACTION_CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    imageView.setImageBitmap(data.extras.get("data") as Bitmap)
                }
            }
            ACTION_ALBUM_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val resolver = this.contentResolver
                    val bitmap =
                            MediaStore.Images.Media.getBitmap(resolver, data?.data)
                    imageView.setImageBitmap(bitmap)
                }
            }
            else -> {
                println("no correspond resultCode found")
            }
        }
    }

}
