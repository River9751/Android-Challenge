package com.example.river.littlebirdsound

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import java.io.File
import java.io.IOException

class RecorderPlayer(val activity: Activity) {

    private lateinit var recorder: MediaRecorder
    private lateinit var soundFile: File

    fun checkPermission() {
        //檢查硬體設備
        if (!activity.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            Toast.makeText(activity, "Your device doesn't have a microphone", Toast.LENGTH_LONG).show()
            return
        }

        //檢查錄音權限
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.RECORD_AUDIO), 0)
        }
    }

    private fun buildMediaFile() {
        val file = File(activity.applicationContext.filesDir.path)

        try {
            soundFile = File.createTempFile("test", ".3gp", file)

            println("created file $soundFile")

        } catch (ex: Exception) {
            println("Setup sound File\", \"failed ${ex.message}")
        }
    }

    fun startRecord() {
        buildMediaFile()
        recorder = MediaRecorder()
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        recorder.setOutputFile(soundFile?.absolutePath)
        recorder.prepare()
        recorder.start()

        Toast.makeText(activity, "Start recording", Toast.LENGTH_SHORT).show()
    }

    fun stopRecord() {
        recorder.stop()
        recorder.release()
        Toast.makeText(activity, "Record finished", Toast.LENGTH_SHORT).show()
    }

    fun playRecord() {
        val player = MediaPlayer()
        player.setDataSource(soundFile.absolutePath)
        player.prepare()
        player.start()
    }
}