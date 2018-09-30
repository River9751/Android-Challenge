ScalableImage
===

![](https://i.imgur.com/9ErOBhF.gif)

目標項目：
- 前置作業
- 拖拉事件的處理
- 縮放事件的判斷

## 前置作業
首先我們需要自訂一個 class 繼承 ImageView

```kotlin
class CustomView : ImageView {
        
}
```
這裡 IDE 會提示錯誤，我們需要在這邊加入建構子
```kotlin
class CustomView : ImageView {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
}
```

接著在主畫面的 layout 檔中加入我們自訂的 ImageView
```xml=
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.example.river.scalableimageview.CustomView
        android:id="@+id/imageView"
        android:layout_width="150dp"
        android:layout_height="150dp"
        android:src="@mipmap/ic_launcher"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</android.support.constraint.ConstraintLayout>
```
> android:src="@mipmap/ic_launcher" 表示該 ImageView 的圖片來源

## 拖拉事件的處理
回到 CustomView，要處理拖拉事件我們需要覆寫 onTouchEvent
```kotlin
override fun onTouchEvent(event: MotionEvent?): Boolean {

        performClick()

        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                //先求出偏移量(此時 View 還沒有移動位置)
                offsetX = (event.x - downX).toInt()
                offsetY = (event.y - downY).toInt()
                setFrame(
                    this.left + offsetX,
                    this.top + offsetY,
                    this.right + offsetX,
                    this.bottom + offsetY
                )
            }
        }
        return true
    }
```

基本思路是，在按下的時候(ACTION_DOWN)，把我們按下當時的座標給記錄下來，然後在移動的時候(ACTION_MOVE)，計算出按下位置的偏移量，然後透過 setFrame() 更新自己的位置。
>event.x 和 event.y 是相對於自身這個View的起點(左上角為 0, 0) 的座標位置。

## 縮放事件的判斷

因為縮放事件我們會用到兩指操作，所以在兩指同時按下時， TouchEvent 會進入 ACTION_POINTER_DOWN 這個 MotionEvent
```kotlin
override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.actionMasked) {
            MotionEvent.ACTION_POINTER_DOWN -> {
                oriDistance = getDistance(
                        event.getX(0),
                        event.getY(0),
                        event.getX(1),
                        event.getY(1)
                )
            }
            MotionEvent.ACTION_MOVE -> {
                val x1 = event.getX(0)
                val y1 = event.getY(0)
                val x2 = event.getX(1)
                val y2 = event.getY(1)

                var distance = getDistance(x1, x2, y1, y2)

                var ratio = distance / oriDistance
                scaleImage(ratio)
                this.oriDistance = distance
            }
        }
        return true
    }
    
fun getDistance(x1: Float, y1: Float, x2: Float, y2: Float): Double {
    return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)).toDouble())
}
```
這邊的作法是，在我們按下兩指之後，去紀錄兩指間的距離
然後在進入 ACTION_MOVE 的時候會得到一個新的距離
再使用這兩個距離得出一個縮放比例，再丟給自訂的 scaleImage() 去處理縮放

```kotlin
 fun scaleImage(ratio: Double) {

        if (ratio > 1) {
            val increasedWidth = this.width * (ratio - 1)
            val increasedHeight = this.height * (ratio - 1)

            val left = this.left - (increasedWidth / 2)
            val right = this.right + (increasedWidth / 2)
            val top = this.top - (increasedHeight / 2)
            val bottom = this.bottom + (increasedHeight / 2)

            setFrame(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())

        } else if (ratio < 1) {
            val decreasedWidth = this.width * (1 - ratio) 
            val decreasedHeight = this.height * (1 - ratio) 

            val left = this.left + (decreasedWidth / 2)
            val right = this.right - (decreasedWidth / 2)
            val top = this.top + (decreasedHeight / 2)
            val bottom = this.bottom - (decreasedHeight / 2)

            setFrame(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        }
    }
```

當我們得到一個比例是放大時，先去求得增加的寬度
再利用寬度去得出應該要有的邊界位置(注意圖如果放大的話，left 和 top 邊界的長度反而是會被縮小的)，縮小同理。