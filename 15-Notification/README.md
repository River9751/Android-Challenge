Notification
===


## 演變
 在 Android 3.0 之前，通知是使用 Notification() 來達成
 ```kotlin
 val notification = Notification(icon, tickerText, when)
 ```
 在 Android 3.0 以後，使用 Notification.Builder 執行通知
 ```kotlin
 val notification = Notification.Builder(context)
 ```
 所以後來為了兼容之前的版本，新增了 NotificationCompat.Builder 來執行通知
 ```kotlin
val notification = NotificationCompat.Builder(context)
 ```
 另外，在 Android API level 26.1.0 只傳入 context 的建構子已被棄用，新的建構子還需多傳入 channel Id
```kotlin
val notification = NotificationCompat.Builder(context, channelId)
 ```
 
## Android 8.0 前使用方式

要成功顯示出通知
1. 首先需要建立一 NotificationManager 物件
2. 建立一 Builder ，並將細節的屬性設置添加給上去
3. 呼叫 Builder.build() 得到 Notification 物件
4. NotificationManager 執行 notify 方法執行通知
```kotlin
val notificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

val builder =
    NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_star_black_24dp)
        .setContentTitle("My notification")
        .setContentText("Hello World!")
        
val notificationId = 1
val notification = builder.build()

notificationManager.notify(notificationId, notification)
```

## Android 8.0 後使用方式
在 Android 8.0 後，除了原本的動作還 "必須" 建立一個 NotificationChannel 並使用 createNotificationChannel() 方法指定給 NotificationManager
>要注意在建立 Builder 時傳入的參數 "channelId" 要與建立的 NotificationChannel 物件帶入的第一個參數一致，這樣才能對應的上


```kotlin
val builder =
    NotificationCompat.Builder(this, "channel01")
        .setSmallIcon(R.drawable.ic_star_black_24dp)
        .setContentTitle("My notification")
        .setContentText("Hello World")
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        .setVibrate(longArrayOf(300, 600, 300, 600))
        .setLights(Color.GREEN, 1000, 1000)
        .setContentIntent(pendingIntent)

val channel = NotificationChannel(
    "channel01",
    "MyChannel",
    NotificationManager.IMPORTANCE_HIGH
)

val notificationId = 1
val notification = builder.build()
notificationManager.createNotificationChannel(channel)
notificationManager.notify(notificationId, notification)
```

## 點擊操作
在按下通知欄的通知時，我們可能會希望可以跳轉到某一個 Activity
這時候可以使用 PendingIntent 來達成
```kotlin
val intent = Intent(this, MainActivity::class.java)
val pendingIntent =
    PendingIntent.getActivity(this, 0, intent, 0)

```
接著在 Builder 建立時呼叫 setContentIntent 方法
```kotlin
 val builder =
            NotificationCompat.Builder(this, "channel01")
                .setSmallIcon(R.drawable.ic_star_black_24dp)
                .setContentTitle("My notification")
                .setContentText("Hello World")
                
                ...
                
                .setContentIntent(pendingIntent)
```