Day 23 LayoutSwitch
===

今天學習如何動態切換 Layout 的排版模式

![](https://i.imgur.com/AIMAsi3.gif)

## 建立右上角切換的圖示

第一步先建立要使用的 menu ，跟之前的做法一樣，在 res 資料夾中建立 menu 資料夾，並新增一 xml 檔案，裡面的 android:icon 就是要顯示的圖案

```xml=
<menu xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto">
    <item
            android:id="@+id/menu_switch_layout"
            android:title="switch"
            app:showAsAction="always"
            android:icon="@drawable/ic_menu_black_24dp"/>
</menu>
```

第二步到 MainActivity 中覆寫 onCreateOptionsMenu 方法
```kotlin
override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_switch, menu)
    return super.onCreateOptionsMenu(menu)
}
```
## RecyclerView
顯示畫面一樣使用 RecyclerView，記得要在 gradle 中加入：
```
implementation 'com.android.support:recyclerview-v7:28.0.0'
```
因為有使用到 CardView 所以也要加入
```
implementation 'com.android.support:cardview-v7:28.0.0'
```
然後建立這次的 ItemModel 類別
```kotlin
data class ItemModel(var name:String, var likeCount:Int, var commentCount:Int)
```
## Adapter
Adapter 的製作方法與之前大致相同，但這次傳入的參數需要多一個 GridLayoutManager，用來判斷當前的 SpanCount

```kotlin
class CusAdapter(
    private val ctx: Context,
    private var list: MutableList<ItemModel>,
    private var gridLayoutManager: GridLayoutManager
) : RecyclerView.Adapter<CusAdapter.CusViewHolder>() 
```
然後定義兩個變數來代表現在是要顯示大的 Item 還是小的 Item，規則是自己訂的
```kotlin
private val smallItem = 1
private val bigItem = 2
```
下一步要覆寫 getItemViewType，這裡依照 GridLayoutManager 目前的 SpanCount 來判斷要回傳的 type ，型別為 Int
```kotlin
override fun getItemViewType(position: Int): Int {
    return when {
        gridLayoutManager.spanCount == 1 -> bigItem
        gridLayoutManager.spanCount == 2 -> smallItem
        else -> 0
    }
}
```
接下來覆寫 onCreateViewHolder 時，我們就可以從參數列拿到剛剛回傳的 viewType，然後決定要做什麼 layout 給它
```kotlin
override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): CusViewHolder {

    val v: View = when (viewType) {
        1 -> LayoutInflater.from(ctx).inflate(R.layout.item_small, p0, false)
        2 -> LayoutInflater.from(ctx).inflate(R.layout.item_big, p0, false)
        else -> LayoutInflater.from(ctx).inflate(R.layout.item_small, p0, false)
    }

    return CusViewHolder(v)
}
```
綁定資料與介面
```kotlin
inner class CusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(position: Int) {
        when (itemViewType) {
            1 -> {
                itemView.smallNameTextView.text = list[position].name
            }
            2 -> {
                itemView.bigNameTextView.text = list[position].name
                itemView.likeTextView.text = "Likes: ${list[position].likeCount}"
                itemView.commentTextView.text = "Comments: ${list[position].commentCount}"
            }
        }
    }
}
```
## 設定切換事件的動作
最後回到 MainActivity 覆寫右上角按鈕按下後的操作
思路為依照目前的 GridLayoutManager.SpanCount 去更換圖示的資源檔

```kotlin
override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == R.id.menu_switch) {
        val spanCount = gridLayoutManager.spanCount
        when (spanCount) {
            1 -> {
                gridLayoutManager.spanCount = 2
                item.icon = resources.getDrawable(R.drawable.ic_menu_black_24dp, null)
            }
            2 -> {
                gridLayoutManager.spanCount = 1
                item.icon = resources.getDrawable(R.drawable.ic_apps_black_24dp, null)
            }
        }
        return true
    }

    return super.onOptionsItemSelected(item)
}
```