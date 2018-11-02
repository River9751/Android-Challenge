package com.example.river.littlebirdsound

import android.content.Context
import android.media.MediaPlayer

class CusMediaPlayer(ctx: Context, val callBack: () -> Unit) {

    var mediaPlayer: MediaPlayer = MediaPlayer.create(ctx, R.raw.cc)

    init {

        mediaPlayer.setOnCompletionListener {
            callBack.invoke()
        }

    }

    fun seekToProgress(progress: Int) {
        mediaPlayer.seekTo(progress)
    }

    fun getDuration(): Int {
        return mediaPlayer.duration
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    fun adjustVolume(value: Float) {
        mediaPlayer.setVolume(value / 100f, value / 100f)
    }

    fun playOrPauseMusic(): String {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            return "Play"
        } else {
            mediaPlayer.start()
            return "Pause"
        }
    }

    fun stopMusic(): String {
        mediaPlayer.seekTo(0)
        mediaPlayer.stop()
        mediaPlayer.prepare()
        return "Play"
    }
}