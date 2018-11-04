今天要學習如何獲取裝置的加速器資訊
並在感應到搖晃時切換圖片和震動

![](https://i.imgur.com/GBvVEyR.gif)

## 準備圖片資源
首先先把需要的圖片資源放到 drawable 中，並建立一個變數來存放它們的 id
```kotlin
lateinit var imageList: ArrayList<Int>
```
```kotlin
private fun initImages() {
    imageList = arrayListOf(
        R.drawable.img01,
        R.drawable.img02,
        R.drawable.img03,
        R.drawable.img04,
        R.drawable.img05,
        R.drawable.img06
    )
}
```
## SensorManager
要偵測硬體的 sensor ，首先要獲得系統感應器的實體，使用以下這一行獲取 SensorManager 的 instance
```kotlin
sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
```
下一步準備 sensorEventListener，在註冊的 Sensor 有變動時會觸發，在後面設立監聽時會用到
在加速的事件中，event.values[0]、event.values[1]、 event.values[2]，分別代表 x、y、z 軸的數值，由於方向不同會有負值，所以用 Math.abs() 方法來取絕對值，在其中一個方向超過我們設定的值後，就觸發變換圖片的方法。
```kotlin
val sensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                val x = Math.abs(event.values[0])
                val y = Math.abs(event.values[1])
                val z = Math.abs(event.values[2])

                if (x > 15 || y > 15 || z > 15) {
                    changeImage()
                }
            }
        }
    }
```
接著從 SensorManager 的 getDefaultSensor 方法指定我們要取得 ACCELEROMETER 的感應器
然後呼叫 SensorManager.registerListener() 方法註冊偵聽器
```kotlin
val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
```

## 變換圖片與震動
達成條件時觸發這個方法，為了防止感應器不斷取得值觸發這個方法，我們建立一個 flag 來防止重複觸發。
接著設定一個變數 current 存放目前顯示到哪一個圖片
最後使用之前用過的 handler.postDelayed 方法，讓這個方法執行後 1 秒才變換 flag ，這樣這段期間內都不會重複觸發。
```kotlin
fun changeImage() {
    if (isChanging) {
        return
    }

    isChanging = true
    if (current != imageList.size) {
        imageView.setImageResource(imageList[current])
        current++
    } else {
        imageView.setImageResource(imageList[0])
        current = 0
    }
    
    ...

    val runnable = Runnable {
        isChanging = false
    }

    isChanging = false
    handler.postDelayed(runnable, 1000)
}
```

## 震動的處理

首先要在 Manifest 中，要加入權限
```xml
<uses-permission android:name="android.permission.VIBRATE" />
```
然後需要兩個物件，Vibrator、VibrationEffect
```kotlin
lateinit var vibrator: Vibrator
lateinit var vibrationEffect: VibrationEffect
```
初始化 vibrator
```kotlin
vibrator = getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
```
由於在 SDK 26 以後，震動的方式不同，所以要加一個判斷
VibrationEffect.createOneShot(1000, 150) 設定執行一次震動，第一個參數是毫秒數，第二個參數是震動強度，值在 1 - 255 之間。
之前的版本就直接呼叫 vibrate() 方法，參數為毫秒數。
```kotlin
if (Build.VERSION.SDK_INT >= 26) {
    vibrationEffect = VibrationEffect.createOneShot(1000, 150)
    vibrator.vibrate(vibrationEffect)
}else{
    vibrator.vibrate(1000)
}
```