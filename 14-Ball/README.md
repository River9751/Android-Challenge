Ball (Animator)
===

今天學習如何做出動畫效果
主要使用 ValueAnimator 和 ObjectAnimator 來達成
- ValueAnimator，將重點放在
透過值的變化來產生動畫，可以將值設定給 View 的 translationX, translationY, alpha 值... 等等；主要是在規範時間內得到不同的值，再看如何去操作這些值
- ObjectAnimator
ObjectAnimator 繼承 ValueAnimator，將重點放在直接操作物件的某一個"屬性"上，藉此來達成動畫的效果

## ValueAnimator

下方程式碼為使用範例：
- ofFloat 代表每一動畫的目標值
- duration 代表動畫時間，單位為毫秒，預設值為 300
- addUpdateListener 裡面就是每一次拿到 Value 要做的事情，animator 會把動畫時間做切割，所以這個 function 不會只進 3 個 float 的 3 次，而是一連串的數值；如範例為改變一連串的數值來移動 Y 軸
- 最後記得要使用 start() 來啟動動畫

```kotlin
val animator = ValueAnimator.ofFloat(0f, -350f, 0f)
animator.duration = 500
animator.addUpdateListener {
    valueBall.translationY = it.animatedValue as Float
}

valueBall.setOnClickListener {
    animator.start()
}
```
## ObjectAnimator

ObjectAnimator 是對物件屬性的操作
所以我們依序傳入物件、屬性名稱、數值組...就可以達成動畫的效果
一樣要注意 start() 才會執行
```kotlin
val animator = ObjectAnimator.ofFloat(
    objectBall,
    "rotationY",
    0.0f, 360.0f)
animator.duration = 500

objectBall.setOnClickListener{
    animator.start()
}
```

## AnimatorSet

AnimatorSet 可以用來把不同的 ObjectAnimator 包成一包執行，可以設定有先後順序或是同時執行等等
下方程式碼先製作了 2 個 animator，接著有個按鈕分別執行 **"連續播放動畫"** 和 **"同時播放動畫"**
- 連續播放動畫
使用 .play() 後可以繼續加 .after() 來串接動畫
- 同時播放動畫
使用 .playTogether() 將傳入的 animator 同時執行


```kotlin
val animator1 = ObjectAnimator.ofFloat(
    setBall01,
    "translationY",
    0f, -500f, 0f
)
val animator2 = ObjectAnimator.ofFloat(
    setBall02,
    "translationY",
    0f, 500f, 0f
)

val animatorSet_onebyone = AnimatorSet()
animatorSet_onebyone.duration = 500

oneByOne.setOnClickListener {

    if (!animatorSet_onebyone.isRunning) {
        animatorSet_onebyone.play(animator1).after(animator2)
        animatorSet_onebyone.start()
    }
}

val animatorSet_sameTime = AnimatorSet()
animatorSet_sameTime.duration = 500

sameTime.setOnClickListener {
    if (!animatorSet_sameTime.isStarted) {
        animatorSet_sameTime.playTogether(animator1, animator2)
        animatorSet_sameTime.start()
    }
}
```

## Interpolator

這是 ObjectAnimator 的其中一個設定，表示說這個動畫的呈現該套用什麼演算法
基本上有以下幾種：
- LinearInterpolator
- AccelerateInterpolator
- DecelerateInterpolator
- AccelerateDecelerateInterpolator
- AnticipateInterpolator
- OvershootInterpolator
- AnticipateOvershootInterpolator
- BounceInterpolator
- CycleInterpolator

套用方式：指定給 animator.interpolator 即可，動畫即會有改變速度愈來愈快、愈來愈慢... 等等不同的效果
```kotlin
val animator = ObjectAnimator.ofFloat(interpolatorBall, "translationX", 0f, 500f, 0f)
animator.interpolator = LinearInterpolator()
animator.start()
```