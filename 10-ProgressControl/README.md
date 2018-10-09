ProgressControl
===
這次要試著嘗試使用 Runnable 物件
和認識 Thread, HandlerThread, Handler, Runnable 之間的關係

目標項目
- 前置作業
- MainActivity
- 按鈕事件處理

## 前置作業
這邊要先了解幾個名詞和他們之間的關係
- MainThread 
主執行續，也是一種 HandlerThread，負責將 MessageQueue
中的事件拿出來分配給專職的 Handler 去處理
- Handler 
可以想像成中間人的角色，負責把事件加到 MessageQueue 中，或是接收 MainThread 分配過來的事件，並再指派給 Thread 處理
- Thread
實際處理任務的角色
- Runnable
要執行的任務內容
- HandlerThread
與 Thread 不同的地方在於，他會有一個 loop 不斷重複執行，接收任務分配後，它會繼續等待任務進來並繼續分配。而 Thread 內容執行後就會結束。
## MainActivity
先宣告幾個我們需要的欄位
handler 就是分配任務的角色
runnable 為實際要執行的任務內容
max 表示進度條的最大值 (0 - 100)
now 為目前的進度
```kotlin
lateinit var handler: Handler
lateinit var runnable : Runnable
var max = 100
var now = 0
```
onCreate
思路為將增加進度條的動作放在 Runnable 中
->**任務內容為增加進度條**
接著建構 handler，並經由 handler 這個中間人 post 任務到 MessageQueue 
->**經由 handler 發送任務到 MessageQueue，postDelayed 表示會有延遲**
>這邊 handler.postDelayed(runnable, 500) 可以解釋為發送執行任務
>而這個動作我們又放在任務本身之中，所以會造成遞迴
>又因為有使用 delay 所以可以達到類似 Timer 定時執行任務的效果
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    progressBar.max = this.max

    setUI()

    handler = Handler()

    runnable = Runnable {
        increaseProgress()
        handler.postDelayed(runnable, 500)
    }

    ...
}
```
## 按鈕事件處理
- 開始鈕
按下後就直接執行 runnable (在這裡呼叫等同於是 MainTrhead 執行這個任務)
- 暫停鈕
使用 removeCallbacksAndMessages(null) 這個方法來移除任務
後面參數 token 為 null 時，handler 的所有 post 和 callback 都會移除
- 停止鈕
同暫停，要記得更新介面
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    start.setOnClickListener {
        runnable.run()
    }

    pause.setOnClickListener {
        pause()
    }

    stop.setOnClickListener {
        pause()
        this.now = 0
        setUI()
    }
    
    ...
}

fun pause() {
    handler.removeCallbacksAndMessages(null)
}

fun setUI(){
    progressBar.progress = now
    textView.text = "$now%"
}
```
