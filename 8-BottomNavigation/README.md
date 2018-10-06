BottomNavigation
===

目標項目：
- 前置作業
- Fragment 設定
- Activity 設定

## 前置作業

要達成下方的自訂導覽列效果，首先加入所需的三個圖示
在 drawable 資料夾右鍵 New -> Vector Asset

![](https://i.imgur.com/QB92VTq.png)


接著在 res 資料夾底下新增 menu 資料夾，並加入 bottom_navigation.xml 檔案，裡面就是下方導覽列的介面設置

![](https://i.imgur.com/o4dZ4FK.png)

```xml=
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/navigation_home"
        android:icon="@drawable/ic_home_black_24dp"
        android:title="@string/title_home" />

    <item
        android:id="@+id/navigation_dashboard"
        android:icon="@drawable/ic_dashboard_black_24dp"
        android:title="@string/title_dashboard" />

    <item
        android:id="@+id/navigation_notifications"
        android:icon="@drawable/ic_notifications_black_24dp"
        android:title="@string/title_notifications" />
</menu>
```

建立好後，我們還需要寫一個自訂 class 繼承 NavigationView

```kotlin
package com.example.river.bottomnavigation

import android.content.Context
import android.support.design.widget.NavigationView

class BottomNavigationView(context: Context?) : NavigationView(context)
```
再來就可以到我們首頁介面的 activity_main 檔中，加入剛建立好的 layout

```xml=
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/container"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <android.support.design.widget.BottomNavigationView
        android:id="@+id/navigation"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:menu="@menu/bottom_navigation" />

</android.support.constraint.ConstraintLayout>
```
>這邊有遇到一個問題，如果不是用
> android.support.design.widget.BottomNavigationView 
> 而是用
> android.support.design.widget.NavigationView 
> 在使用這個 view 時會無法設定 setOnNavigationItemSelectedListener
> 尚未查到原因

## Fragment 設定
三個圖示按下時會對應三個 Fragment
- HomeFragment(一併設定要切換到第二個 Activity 的按鈕事件)
```kotlin
class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {

        homeButton.setOnClickListener {
            val intent = Intent(activity,Home2Activity::class.java)
            startActivity(intent)
        }

        super.onStart()
    }
}
```
- DashboardFragment
```kotlin
class DashboardFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }
}
```

- NotificationFragment
```kotlin
class NotificationFragment  : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }
}
```

## Activity 設定
這邊就從第二個 Activity 開始(第一個 Activity 為 MainActivity)
先自訂一個 Home2Activity 類別
使用 setDisplayHomeAsUpEnabled(true) 來顯示左上角的返回按鈕
並覆寫 onOptionsItemSelected 方法來觸發 onBackPressed 回上一頁
```kotlin
class Home2Activity: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.home2)

        this.title = "Home 2"
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        home2Button.setOnClickListener {
            val intent = Intent(this,Home3Activity::class.java)
            startActivity(intent)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
```
接著是第三個 Activity，按鈕設定回到 MainActivity
```kotlin

class Home3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home3)


        this.title = "Home 3"
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        home3Button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
```