Day 22 ActivityTransition
===

這次要實現的是在 Activity 間的轉場效果

![](https://i.imgur.com/DeEDfI0.gif)

## MainActivity
Explode、Slide 和 Fade 這三個是使用相同的轉場方法
首先在對應的按鈕上加入事件，並呼叫同一個自訂方法傳入不同參數

```kotlin
explode.setOnClickListener { startTransition("explode") }
slide.setOnClickListener { startTransition("slide") }
fade.setOnClickListener { startTransition("fade") }
```
按下按鈕後會跳轉到 SecondActivity ，目標使用 StartActivity 把我們的參數帶過去。
- 先在 intent 中把 type 放到 extra 中
- 接著做出轉場的 ActivityOptions 
- StartActivity 第二個參數型別為 Bundle，所以做出來的 ActivityOptions 還要使用 .toBundle() 轉型帶入

```kotlin
private fun startTransition(type: String) {
    val intent = Intent(this, SecondActivity::class.java)
    intent.putExtra("type", type)
    val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this)
    startActivity(
        intent,
        activityOptions.toBundle()
    )
}
```
## SecondActivity 轉場
在 SecondActivity 這邊接收轉場資訊，首先把 intent 中的 type 取出來，接著傳給 init() 方法把三種轉場效果相關屬性設定好
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val type:String? = intent.getStringExtra("type")
    if  (type != null){
        init(type)
    }

    setContentView(R.layout.activity_second)
}

private fun init(type: String) {
    when (type) {
        "explode" -> {
            val explodeTransition = Explode()
            explodeTransition.duration = 2000
            window.enterTransition = explodeTransition
            window.exitTransition = explodeTransition
        }

        "slide" -> {
            val slideTransition = Slide()
            slideTransition.duration = 2000
            window.enterTransition = slideTransition
            window.exitTransition = slideTransition
        }

        "fade" -> {
            val fadeTransition = Fade()
            fadeTransition.duration = 2000
            window.enterTransition = fadeTransition
            window.exitTransition = fadeTransition
        }
    }
}
```

## View 的轉場效果
第二種轉場方式為，關聯兩個 Activity 中的 View，達成移動的轉場效果(其實兩個 Activity 中各有一個 View)

MainActivity 中，按下圖片時，把畫面上要轉場的 View 做成 Pair<View, String> 的 Pair，並加到 ActivityOptions 之中；Pair 中的字串在 SecondActivity 會用到。
```kotlin
private fun secondClick() {
    val intent = Intent(this, SecondActivity::class.java)
    val v1 = catImage01 as View
    val v2 = secondImage01 as View

    val p1 = android.util.Pair(v1, "catTrans")
    val p2 = android.util.Pair(v2, "secondTrans")
    val transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
        this, p1, p2
    )

    startActivity(intent, transitionActivityOptions.toBundle())
}
```

## SecondActivity 綁定

在第一個畫面設定好 Pair 後，我們只需要在第二個畫面的 xml 設定屬性，就會自動關聯上，十分方便。
```xml=
<ImageView
    android:id="@+id/catImage02"
    ...
    android:src="@drawable/cat"
    android:transitionName="catTrans" />
    
<ImageView
    android:id="@+id/secondImage02"
    ...
    android:src="@drawable/second"
    android:transitionName="secondTrans" />
```