WebViewSearch
===
**WebView 的基本使用**

![](https://i.imgur.com/oFLjDvz.gif)

目標項目：
- WebView 設定
- 縮小鍵盤

## WebView 設定

目標加入 WebView ，然後使用 EditText 中的文字搜尋
首先我們先在 activity_main.xml 中加入：
```xml=
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        android:text="Search"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <WebView
        android:id="@+id/webView"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_marginTop="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/editText" />

    <EditText
        android:id="@+id/editText"
        android:layout_width="250dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        android:inputType="text"
        app:layout_constraintEnd_toStartOf="@+id/button"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</android.support.constraint.ConstraintLayout>
```

接著在 MainActivity 之中建立 WebViewClient 的實體，並指定給 WebView
```kotlin
val webViewClient = WebViewClient()
webView.webViewClient = webViewClient
```

最後我們只要呼叫 WebView 的 loadUrl() 方法，就可以成功加載頁面
```kotlin
val URL = "https://google.com.tw"
webView.loadUrl(URL)
```
接著要將 EditText 輸入的文字，當作參數交給 Google 搜尋
在按下搜尋按鈕時執行
```kotlin
button.setOnClickListener {
    webView.loadUrl(URL + "/search?q=" + editText.text)
}
```
便可以將參數放到 google 的網址列傳過去伺服器

## 縮小鍵盤
在輸入完文字搜尋之後，我們發現鍵盤並沒有消失
所以這邊需要手動縮小鍵盤
```kotlin
button.setOnClickListener {
    webView.loadUrl(URL + "/search?q=" + editText.text)
    hideSoftInput()
}
fun hideSoftInput() {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE)
        as InputMethodManager
    imm.hideSoftInputFromWindow(editText.windowToken, 0)
}
```
hideSoftInputFromWindow 的兩個參數
一個是 View 的 WindowToken； 另一個是 flag ，與顯示鍵盤時所帶入的 flag 有關
如果要確保關掉鍵盤，我們可以直接輸入 0

>另一種比較簡單的方式是在 EditText 的 XML 之中，直接加入屬性
>```xml=
>android:imeOptions="actionDone"
>```
>便可以在輸入完文字後，直接確認並縮小鍵盤