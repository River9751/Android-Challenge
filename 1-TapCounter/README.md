TapCounter 
===
**使用 Android Studio 實作一簡易計數器**

![](https://i.imgur.com/EQNfec6.gif)
有幾個目標項目要做：
- 自訂 OptionsMenu
- 計數按鈕
- 歸零按鈕

## 自訂 OptionsMenu
首先在 res 目錄加入 menu 資料夾，並加入自訂選單的 xml 檔，命名 menu_main.xml

![](https://i.imgur.com/K4CcRmP.png)

在 menu_main.xml 加入 item ，並給予相關屬性
>app如果顯示紅字需在上方 menu tag 中加入 ```xmlns:app="http://schemas.android.com/apk/res-auto" ```

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <item
        android:id="@+id/resetItem"
        android:title="Reset"
        app:showAsAction="always" />
</menu>
```
接著在 MainActivity 中覆寫 onCreateOptionsMenu 方法

```kotlin
override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    this.menuInflater.inflate(R.menu.menu_main, menu)
    return true
}
```
## 計數按鈕
在 MainActivity 的 onCreate 方法中設定按下 Button 要處理的事情
```kotlin
tapButton.setOnClickListener {
        number++
        countView.text = number.toString()
}
```

## 歸零按鈕
在 MainActivity 中覆寫 onOptionsItemSelected 方法
```kotlin
override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item != null && item.itemId == R.id.resetItem) {
        number = 0
        countView.text = number.toString()
    }
    return true
}
```