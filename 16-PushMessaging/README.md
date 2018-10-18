PushMessages
===
今天要學習使用 Firebase 的通知推送功能和廣播物件的使用
## FirebaseMessagingService
在 Android Studio 3 之中，Firebase 已經和 IDE 整合的很好
可以從工具快速的建好使用環境
首先到上方工具列 Tools -> Firebase

![](https://i.imgur.com/ia8zpHe.png)

選擇 Cloud Messaging -> Set up Firebase Cloud Messaging

![](https://i.imgur.com/JRpUr6C.png)

接下來至少還有兩個準備動作要做
1. 要把現在這個專案跟 firebase 做連結，按下之後需要登入你的 Google 帳號
2. 把 FCM 相關的包加入到專案中

![](https://i.imgur.com/kebGuyG.png)

**加入 FCM SDK**
IDE 的連結按下後會跳出提示視窗，表示它會在對應的地方自動幫我們加上那幾行字，之後便完成基礎的設置

![](https://i.imgur.com/LYrsSHy.png)

但是這邊有遇到一個問題，加入這一行後 gradle 會報錯無法 sync
```  implementation 'com.google.firebase:firebase-messaging:17.0.0:15:0.0'```
到官網查詢後，改成以下即可
```implementation 'com.google.firebase:firebase-messaging:17.3.4'```
>會跳出和 ```com.android.support:appcompat-v7:28.0.0```衝突的紅字，但是還是可以 Build

最後還需要在 Manifest 中加入以下，其中 name 是你負責處理接收訊息的 FirebaseMessagingService 類別
```
<service android:name=".java.MyFirebaseMessagingService">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>
```

## FirebaseMessagingService
首先寫一個自訂類別來繼承 FirebaseMessagingService，並覆寫 onMessageReceived 方法；主要思路為在接收到雲端發送的訊息時，使用 LocalBroadcastManager 去推送廣播，並把 intent 傳出去，intent 中包含我們從雲端上得到的資訊

```kotlin
class CusMessagingService : FirebaseMessagingService() {

    lateinit var broadcastManager: LocalBroadcastManager

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        broadcastManager = LocalBroadcastManager.getInstance(this)

        val intent = Intent("CusMessage")

        intent.putExtra("from", remoteMessage?.from.toString())

        if (remoteMessage?.notification != null) {
            intent.putExtra("body", remoteMessage.notification?.body)
        }

        broadcastManager.sendBroadcast(intent)
    }
}
```
## BroadcastReceiver
推送廣播的一端處理好了，接下來處理接收的物件
自訂一個類別繼承 BroadcastReceiver 並覆寫 onRecevie 方法
方法中的 intent 就是推送廣播時所傳入的 intent ，這裡我們就可以把資料取出來顯示了

```kotlin
class CusBroadcastReceiver(val ctx: Context) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        //showNotification()
        showDialog(intent)
    }

    
    fun showDialog(intent: Intent?) {
        AlertDialog.Builder(ctx)
            .setTitle(intent?.getStringExtra("from"))
            .setMessage(intent?.getStringExtra("body"))
            .show()
    }
}
```

## MainActivity

在 MainActivity 之中，主要思路為
初始化時建立廣播物件和接收物件
跳出畫面時註冊廣播
離開畫面時取消廣播
>這邊沒有任何 code 提到 FirebaseMessagingService 是因為它在 Manifest 中指定好類別了自動就會執行

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var localBroadcastManager: LocalBroadcastManager
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        broadcastReceiver = CusBroadcastReceiver(this)
    }

    override fun onResume() {
        localBroadcastManager.registerReceiver(broadcastReceiver, IntentFilter("CusMessage"))
        super.onResume()
    }

    override fun onPause() {
        localBroadcastManager.unregisterReceiver(broadcastReceiver)
        super.onPause()
    }
}
```

## 補充
在 Manifest 之中，我們還可以直接設定 Receiver，只要有符合它 intent-filter 的 action 的廣播通知，就會觸發你設定給它的類別，執行 onReceive 中要做的事。例如：

```
<receiver android:name=".BootCompleteReceiver">
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED"/>
    </intent-filter>
</receiver>
```
以上的意思為在開機完成時，執行我的 BootCompleteReceiver 中規範 Reveive 所要做的事情。
>記得在 Manifest 中加入 '''android.permission.RECEIVE_BOOT_COMPLETED''' 權限
