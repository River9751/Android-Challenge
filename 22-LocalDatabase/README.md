Day 24
===

今天學習如何儲存資料到 SQLite 資料庫

![](https://i.imgur.com/gl7qr8Q.gif)

## SQLiteOpenHelper

首先自訂一個類別繼承 SQLiteOpenHelper，建構子帶入 Context 和一個 CallBack ，用來通知新增資料完成
而 SQLiteOpenHelper 本身建構時需要帶入 4 個參數
- Context
- db 名稱
- 建造 Cursor 的 factory ，預設可以填 null
- 資料庫版本，至少要從 1 開始

```kotlin
class CusSQLiteOpenHelper(ctx: Context, val callBack: () -> Unit) : SQLiteOpenHelper(
    ctx,
    "test.db",
    null,
    1
) 
```
接著覆寫 onCreate 方法，並先執行一段 SQL 來建立出我們的 Table
呼叫 execSQL 方法來執行它
```kotlin
override fun onCreate(db: SQLiteDatabase?) {
    val sql =
        "CREATE TABLE IF NOT EXISTS $tableName ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)"
    db?.execSQL(sql)
}
```
下一步寫一個方法來新增資料
建立一個 ContentValues 物件，可以用來儲存 key - value 的資料
然後呼叫 writableDatabase.insert 方法把資料寫到 table 之中
接著執行我們傳入的 CallBack 通知 MainActivity 資料寫入好了
```kotlin
fun addUser(userName: String) {
    try {
        val values = ContentValues()
        values.put("name", userName)
        writableDatabase.insert(tableName, null, values)
        callBack.invoke()
    } catch (ex: Exception) {
        println(ex.message)
    }
}
```
這個類別還需要提供一個方法讓我們能把資料都撈回來
建立一個 Cursor 一筆一筆讀取資料，然後加入到 users List 中，最後回傳結果
```kotlin
fun getUsers(): MutableList<ItemModel> {

    val cursor =
        readableDatabase.rawQuery("SELECT * FROM $tableName", null)

    val users = mutableListOf<ItemModel>()

    if (cursor.moveToFirst()) {
        do {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val name = cursor.getString(cursor.getColumnIndex("name"))
            users.add(ItemModel(id, name))
        } while (cursor.moveToNext())
    }

    cursor.close()

    return users
}
```
## Adapter

顯示資料一樣使用 RecyclerView，但在 Adapter 中，多寫一個更新顯示 list 的方法，這樣能把我們新拿到的資料做更新
```kotlin
fun updateList(newList: MutableList<ItemModel>) {
    this.list = newList
    this.notifyDataSetChanged()
}
```

## MainActivity

右上角的 icon 跟之前的新增方式一樣
以下是按下後要執行的方法
按下之後建立一個 AlertDialog 來提供輸入介面
確認後呼叫自訂 SQLiteOpenHelper 的 addUser 方法就可以新增一筆資料了
```kotlin
private fun addUser() {

    val editText = EditText(this)

    val builder = AlertDialog.Builder(this)
        .setTitle("Add User")
        .setMessage("Enter your name:")
        .setView(editText)
        .setPositiveButton("Add") { dialog, id ->
            helper.addUser("${editText.text}")
        }

    builder.show()
}
```