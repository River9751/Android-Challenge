ImageList
===
**RecyclerView的使用**



比起 ListView，RecyclerView 有更好的記憶體管理，也可以方便的連結 layout

目標項目：
- 前置作業
- 設定圖片來源
- Adapter 的處理
- 綁定資料

## 前置作業
首先在 gradle 中加入
```
dependencies {
    ...
    implementation 'com.android.support:recyclerview-v7:28.0.0'
}
```
接著在 activity_mail 中就可以加入 RecyclerView
```xml=
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</android.support.constraint.ConstraintLayout>
```
對於要顯示每一組資料，我們需要自訂一個類別來儲存它，基本上這邊會對應到我們的 itemView 上；以這個範例來說，表示我們畫面上會有一個 image 和 一段字，image 的話只要記錄 ResourceId 就可以了
```kotlin
package com.example.river.imagelist

data class ImageModel(val imgId: Int, val memo: String)
```
>在 kotlin 中要表示數據類的 class 可以在前面加上 data 關鍵字
>且要建立一簡易類別可以直接寫在簽名列中一行完成，十分方便

回到 MainActivity.kt，RecyclerView 需要設定 layoutManager
並且需要建立一個 adapter 指定給它
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    recyclerView.layoutManager = LinearLayoutManager(this)
    var adapter = Adapter(this, getDataList())
    recyclerView.adapter = adapter
}
```

## 設定圖片來源
要建立 Adapter 之前，我們需要準備好我們的資料來源
以便在建構 Adapter 的時候當作參數傳進去
首先要把我們準備好的圖片拉進去 drawable 資料夾

![](https://i.imgur.com/9AvE5sK.png)

因為我們的檔名是有規律的，所以可以使用 for 迴圈去組出我們要的檔名，且利用檔名可以從 resources 中得到圖片的 resourcesId；接著做成 ImageModel，放到 list 之中
```kotlin
fun getDataList(): MutableList<ImageModel> {
    val list = arrayListOf<ImageModel>()
    for (i in 0..5) {
        val str = "img${String.format("%02d", i+1)}"
        list.add(ImageModel(findResourceIdByString(str), str))
    }
    return list
}

fun findResourceIdByString(str: String): Int {
    return this.resources.getIdentifier(str, "drawable", this.packageName)
}
```

## 建立 Adapter
資料準備好了，下一步是建立 RecyclerView 所需的 Adapter
Adapter 是用來將資料連結介面的類別，以下是完整的 Adapter
```kotlin

class Adapter : RecyclerView.Adapter<Adapter.MyViewHolder> {

    var context: Context
    var dataList: MutableList<ImageModel>

    constructor(context: Context, dataList: MutableList<ImageModel>) : super() {
        this.context = context
        this.dataList = dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        ...
    }

    override fun getItemCount(): Int {
        ...
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       ...
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var memo = view.memo
        var imageView = view.imageView
    }
}
```

我們自訂的 Adapter 需要覆寫 3 個方法：
- onCreateViewHolder
- getItemCount
- onBindViewHolder

**onCreateViewHolder**
裡面要回傳 RecyclerView.ViewHolder，可以把它想成是處理每一個 item view 的人；所以這邊首先要建立每個項目所要使用的 layout 檔
```xml=
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:scaleType="centerCrop"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/memo"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:gravity="center"
        android:text="TextView"
        android:textSize="30sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/imageView" />

</android.support.constraint.ConstraintLayout>
```
並自訂我們的 ViewHolder 去增加我們要的屬性，用意是連結到我們 layout 上的 id
```kotlin
inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var memo = view.memo
    var imageView = view.imageView
}
```
接著就可以在 onCreateViewHolder 中使用 LayoutInflater 得到我們的 item View，然後再當作參數建立出 ViewHolder 回傳
```kotlin
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
    return MyViewHolder(view)
}
```

**getItemCount**
這裡是單純告訴 adapter 我們用幾個項目，所以回傳資料來源的 size
```kotlin
override fun getItemCount(): Int {
    return dataList.size
}
```

**onBindViewHolder**
最後的 onBindViewHolder 作用是將"值"對應給介面，藉由傳入的 position 變數，我們可以知道這是第幾個項目

```kotlin
override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    holder.imageView.setImageResource(dataList[position].imgId)
    holder.memo.text = dataList[position].memo
}
```
***
Adapter 完成後，就可以在 MainActivity 使用了
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    recyclerView.layoutManager = LinearLayoutManager(this)
    var adapter = Adapter(this, getDataList())
    recyclerView.adapter = adapter
}
```