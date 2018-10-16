LocalStorage
===

在 Android 系統中，要在本地儲存資料可以使用 SharedPreference 或 SQLite
這邊介紹使用 SharedPreference

目標項目：
- CallBack Interface
- Preference
- 在 MainActivity 中使用

## CallBack Interface
這個 Interface 要拿來定義操作資料和 CallBack 回傳
insert 新增資料，select 取得資料
>CallBack 用意為用來通知介面操作資料是否有成功

```kotlin
interface IDataHandler {
    fun insert(input:String, cb:CallBack)
    fun select(key:String, cb:CallBack)

    interface CallBack{
        fun onSuccess(info:String)
        fun onFailure(ex:String)
    }
}
```

## Preference
然後建立 SharedPreference 的類別，並實作上面定義的 IDataHandler 介面 
思路為執行 insert 或 select 這種跟硬體拿資料的動作時，執行成功或發生問題就執行對應的 onSuccess 或 onFailure 通知介面操作結果，並把結果傳回去
```kotlin
class Preference : IDataHandler {

    var preference: SharedPreferences

    constructor(context: Context) {
        preference =
                context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    }

    override fun insert(input: String, cb: IDataHandler.CallBack) {
        val editor = preference.edit()
        try {
            editor.putString("name", input).apply()
            cb.onSuccess(input)
        } catch (ex: Exception) {
            cb.onFailure(ex.message.toString())
        }
    }

    override fun select(key: String, cb: IDataHandler.CallBack) {
        try {
            val result = preference.getString(key, "Default")
            cb.onSuccess(result!!)
        } catch (ex: Exception) {
            cb.onFailure(ex.message.toString())
        }
    }

    fun selectAll(): MutableList<String> {
        var list = mutableListOf<String>()
        for (i in preference.all.keys) {
            val value = preference.getString(i, "Default")
            list.add(value!!)
        }
        return list
    }

}
```
## 在 MainActivity 中使用
save 按鈕按下時，呼叫 Preference 實體的 insert 方法，並把 CallBack 做出來當作參數傳進去
next 按鈕按下時，則把拿到的結果放 intent 並傳到 ResultActivity 
```kotlin
val preference = Preference(this)

    save.setOnClickListener {
        preference.insert(editText.text.toString(), object : IDataHandler.CallBack {
            override fun onSuccess(info: String) {
                Toast.makeText(
                    this@MainActivity, "name $info saved", Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(ex: String) {
                Toast.makeText(
                    this@MainActivity, "Failed to save data", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    next.setOnClickListener {
        preference.select("name", object : IDataHandler.CallBack {
            override fun onSuccess(info: String) {
                val intent = Intent(this@MainActivity, ResultActivity::class.java)
                intent.putExtra("name", info)
                startActivity(intent)
            }

            override fun onFailure(ex: String) {
                Toast.makeText(
                    this@MainActivity, "Failed to read data $ex", Toast.LENGTH_SHORT
                ).show()
            }
        })

    }
```
