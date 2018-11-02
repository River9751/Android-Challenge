Day 20 MediaPlayer/MediaRecorder (上)
===

![](https://i.imgur.com/k3YQ3Y9.gif)


今天學習使用 MediaPlayer 來播放 mp3 檔案
首先要建立 MediaPlayer 類別
這邊的想法是，建構類別時，把完成後 UI 要做的事情當作 CallBack 傳進來
然後在播放器的完成事件裡觸發它。
其它為相關的方法，後面會一一討論。
```kotlin
class CusMediaPlayer(ctx: Context, val callBack: () -> Unit) {

    var mediaPlayer: MediaPlayer = MediaPlayer.create(ctx, R.raw.cc)

    init {
        mediaPlayer.setOnCompletionListener {
            callBack.invoke()
        }
    }

    fun seekToProgress(progress: Int) {...}

    fun getDuration(): Int {...}

    fun getCurrentPosition(): Int {...}

    fun adjustVolume(value: Float) {...}

    fun playOrPauseMusic(): String {...}

    fun stopMusic(): String {...}
}
```

## 綁定音樂資源
首先我們要在 res 資料夾中先新增一個命名為 raw 的資料夾，並把要使用的 mp3 檔案放進去
接著就可以使用 MediaPlayer.create 方法建立 player
```kotlin
var mediaPlayer: MediaPlayer = MediaPlayer.create(ctx, R.raw.cc)
```

## 開始與暫停播放
由於播放和暫停鈕是同一個按鈕，我們希望在同一個方法處理它
可以使用 isPlaying 來判斷是否正在播放
最後順便把按鈕應該顯示的文字回傳回去
```kotlin
fun playOrPauseMusic(): String {
    if (mediaPlayer.isPlaying) {
        mediaPlayer.pause()
        return "Play"
    } else {
        mediaPlayer.start()
        return "Pause"
    }
}
```
## 停止音樂
停止音樂時，首先要把播放的進度使用 seekTo() 方法回歸到 0 (以毫秒計算)
接著呼叫 stop() 方法停止
由於 MediaPlayer 也有自己類似生命週期的定義，所以在 stop() 後會發現其他方法如 start()、pause()...等會失效，此時就要使用 prepare() 方法重新準備好它。
```kotlin
fun stopMusic(): String {
    mediaPlayer.seekTo(0)
    mediaPlayer.stop()
    mediaPlayer.prepare()
    return "Play"
}
```

## 調整音量
呼叫 setVolume() 方法來設定，有分左聲道和右聲道，100% 為 1f
```kotlin
fun adjustVolume(value: Float) {
    mediaPlayer.setVolume(value / 100f, value / 100f)
}
```
在 MainActivity 設定 SeekBar 的 setOnSeekBarChangeListener，會在拉動 SeekBar 時觸發。
```kotlin
 seekBarVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                cusMediaPlayer.adjustVolume(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
```


## 設定音樂進度條
一樣是在 onProgressChanged 事件中去呼叫事件，實際是呼叫自訂類別裡的 
seekTo() 方法來達成
這邊要注意到我們需要一個 flag 來判斷是不是正在做 "拉動" 這個動作，因為後面我們會使用 Runnable 來讓進度條自動跟著音樂的進度前進，如果沒有做這個判斷的話，這邊就會在沒拉動的時候也不斷觸發。
```kotlin
 seekBarProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                isSeeking = false
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isSeeking = true
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (isSeeking) {
                    cusMediaPlayer.seekToProgress(progress)
                }
            }
        })
```
```kotlin
fun seekToProgress(progress: Int) {
    mediaPlayer.seekTo(progress)
}
```

## 讓進度條跟著音樂進度更新
首先在 MainActivity 中，先把進度條的總長度設定成跟音樂的總毫秒數一樣多
```kotlin
seekBarProgress.max = cusMediaPlayer.getDuration()
```
```kotlin
fun getDuration(): Int {
    return mediaPlayer.duration
}
```
接著寫一個更新進度條的方法，思路為在 runnable 中呼叫 runnable，達成類似 Timer 的效果
也就是每 500 毫秒更新一次進度條
```kotlin
private fun asyncProgressBar() {
    handler = Handler()
    runnable = Runnable {
        seekBarProgress.progress = cusMediaPlayer.getCurrentPosition()
        handler.postDelayed(runnable, 500)
    }
    runnable.run()
}
```
```kotlin
fun getCurrentPosition(): Int {
    return mediaPlayer.currentPosition
}
```

後續動畫和錄音部分會放在下篇討論。

Day 21 MediaPlayer/MediaRecorder (下)
===

![](https://i.imgur.com/k3YQ3Y9.gif)

接續上篇對播放器的操作，這篇要加上動畫效果和錄音的部分。

## 顯示動畫

這邊使用到以前使用過的 ObjectAnimator 來達成效果
以下為設定
- rotationY 表示繞著 Y 軸旋轉
- 0.0f, 360.0f 表示從 0 度轉到 360 度
- duration 表示完成一次動畫需要幾毫秒
- repeatCount 表示重複幾次，型別為 Int，可以使用 ValueAnimator.INFINITE 來表示無限循環

```kotlin
animator = ObjectAnimator.ofFloat(
        imageView,
        "rotationY",
        0.0f, 360.0f
    )
    animator.duration = 4000
    animator.repeatCount = ValueAnimator.INFINITE
```
接著要再開始播放音樂的時候啟動動畫
因為播放和暫停的按鈕是同一顆，所以我們要寫個判斷去執行開始、暫停、繼續等動作

```kotlin
when {
    animator.isPaused -> {
        animator.resume()
    }
    animator.isRunning -> {
        animator.pause()
    }
    else -> {
        animator.start()
    }
}
```
停止的時候使用 end()
```kotlin
animator.end()
```
## 錄音

```xml=
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```

```kotlin
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
```
## 建立空白音訊檔案
首先要建立一個空白檔案對象給 MediaPlayer 寫入。
使用 activity.applicationContext.filesDir.path 得到內部儲存空間的檔案目錄
createTempFile 方法的第一個參數為檔案名稱的前綴，第二個參數為後綴，也就是檔案類型
```kotlin
private fun buildMediaFile() {
    val file = File(activity.applicationContext.filesDir.path)

    try {
        soundFile = File.createTempFile("test", ".3gp", file)

        println("created file $soundFile")

    } catch (ex: Exception) {
        println("Setup sound File\", \"failed ${ex.message}")
    }
}
```

## 開始錄音
setAudioEncoder() 方法要放在 setOutputFormat() 跟 prepare() 中間
```kotlin
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
```

## 停止錄音
```kotlin
fun stopRecord() {
    recorder.stop()
    recorder.release()
    Toast.makeText(activity, "Record finished", Toast.LENGTH_SHORT).show()
}
```
停止錄音後，可以看到檔案儲存至內部儲存空間，檔名除了建立空白檔案時建立的前綴和後綴外，中間還多了一串亂數號碼。

![](https://i.imgur.com/YxM5HD2.png)

## 播放錄音

最後快速建立一個 MediaPlayer 來播放音訊，路徑使用 soundFile.absolutePath 來取得。
```kotlin
fun playRecord() {
    val player = MediaPlayer()
    player.setDataSource(soundFile.absolutePath)
    player.prepare()
    player.start()
}
```
