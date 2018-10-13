GithubStars
===

這次要試著接 github api 去顯示使用者按過星星的專案
會使用到 OkHttp 來接 api 和 RecyclerView 來顯示資料


目標項目
- 前情提要
- 安裝與使用 OkHttp
- JSONArray 的使用
- 顯示資料到 RecyclerView 上
- Parcelable 的使用

## 前情提要
提供給 RecyclerView 顯示的 model 類別為 ProjectModel
資源檔中的 item.xml 為 RecyclerView 中要顯示的每一個 item 的介面檔
資源檔中的 info.xml 為按下搜尋後第二頁的介面檔 

## 安裝與使用 OkHttp
要完整的安裝 OkHttp 需要在 gradle 中加入以下資源才能正常使用 OkHttp
```
implementation 'com.squareup.okhttp3:okhttp:3.11.0'
implementation 'com.squareup.okio:okio:2.1.0'
```

接著建立一個 OkHttpHelper 類別，做一個 getResponse 方法來接 request 回來的結果
目標輸入 api 的 url 輸出 response 的 JSONArray
```kotlin
class OkHttpHelper {

    companion object {
        
        fun getResponse(url: String): JSONArray {
            
            val client = OkHttpClient()

            val requestBuilder = Request.Builder()

            val request = requestBuilder
                .url(url)
                .build()

            val response = client.newCall(request).execute()

            val result = response.body()!!.string()

            return JSONArray(result)
        }
    }
}
```
## JSONArray 的使用
回到 MainActivity 在 onCreate 方法中加入按鈕事件
首先要注意使用 OkHttp 需要在背景執行續執行，否則會報錯
然後注意 Thread 的最後一定要寫 .start() 不然他是不會動的
```kotlin
 search.setOnClickListener {
            val username = editText.text
            val url = "https://api.github.com/users/$username/starred"

            Thread {
                var response = OkHttpHelper.getResponse(url)

                var projectList = getProjectList(response)

                var intent = Intent(this, InfoActivity::class.java)

                intent.putParcelableArrayListExtra("projects", ArrayList(projectList))

                startActivity(intent)
            }.start()
        }
```

思路為經由 OkHttp 取得 response 回來的 JSONArray
再把 JSONArray 的資料轉成我們要的 projectList
以下是 getProjectList 方法

> 要注意 for 迴圈中的 until 使用
> for(i in 0 .. 3) => 會印出 0, 1, 2, 3
> for(i in 0 until 3) => 會印出 0, 1, 2

```kotlin
fun getProjectList(json: JSONArray): MutableList<ProjectModel> {

    val projectList = mutableListOf<ProjectModel>()
    for (i in 0 until json.length()) {
        val item = json.getJSONObject(i)

        val owner = item.getJSONObject("owner")
        val ownerName = owner.get("login").toString()
        val avatarURL = owner.get("avatar_url").toString()
        val projectName = item.get("name").toString()
        val description = item.get("description").toString()
        val starCount = item.get("stargazers_count").toString().toInt()
        val forkCount = item.get("forks_count").toString().toInt()

        val project =
            ProjectModel(projectName, description, avatarURL, starCount, forkCount, ownerName)
        projectList.add(project)
    }

    return projectList
}
```

## 顯示資料到 RecyclerView 上
第二個 Activity 用來顯示 RecyclerView 的資料
同樣的要設定 layoutManager 和 adapter 給 RecyclerView

```kotlin
class InfoActivity : AppCompatActivity {
    constructor() : super()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.info)

        recyclerView.layoutManager = 765rLinearLayoutManager(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val projects: ArrayList<ProjectModel> =
            intent.extras.getParcelableArrayList("projects")
        val adapter = CustomAdapter(this, projects)
        recyclerView.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
```
> supportActionBar?.setDisplayHomeAsUpEnabled(true) 設定返回鈕
> 覆寫 onOptionsItemSelected 設定按下的實際行為

## RecyclerView.Adapter

這邊著重在自訂的 ViewHolder
```kotlin
inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(model: ProjectModel) {
        view.projectTextView.text = model.projectName
        view.descriptionTextView.text = model.description
        view.starTextView.text = model.starCount.toString()
        view.forkTextView.text = model.forkCount.toString()
        view.usernameTextView.text = model.username
    }
}
```
Adapter 目的在於綁定你的資料和 UI 
細節可以看 https://hackmd.io/zorRsmnhSTGo9OkBFNlOCw

## Parcelable 的使用
在從一個 Activity 跳轉到另一個 Activity 時，我們通常會使用 intent 來傳遞我們要帶過去的數值，一般 intent 能夠帶的值會是 int/String 等基本型別
但這次需求我們會希望把 api response 回來的資料做成 List 之後傳到第二個 Activity
所以我們需要讓我們的資料型別做一些修改
```kotlin
data class ProjectModel(
    val projectName: String,
    val description: String,
    var avatarURL: String,
    var starCount: Int,
    var forkCount: Int,
    var username: String
) 
```
這是一開始我們 data class 的基礎宣告
接著在 ProjectModel 上按下 alt + enter

![](https://i.imgur.com/xL4d2LV.png)

ide 會幫我們把類別繼承 Parcelable 

```kotlin

data class ProjectModel(
    val projectName: String,
    val description: String,
    var avatarURL: String,
    var starCount: Int,
    var forkCount: Int,
    var username: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(projectName)
        parcel.writeString(description)
        parcel.writeString(avatarURL)
        parcel.writeInt(starCount)
        parcel.writeInt(forkCount)
        parcel.writeString(username)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProjectModel> {
        override fun createFromParcel(parcel: Parcel): ProjectModel {
            return ProjectModel(parcel)
        }

        override fun newArray(size: Int): Array<ProjectModel?> {
            return arrayOfNulls(size)
        }
    }

}
```
接著回到 infoActivity，在按下按鈕事件中，intent 就可以加入我們自訂型別的資料
```kotlin
var intent = Intent(this, InfoActivity::class.java)
intent.putParcelableArrayListExtra("projects", ArrayList(projectList))
```
onCreate 中就可以接到資料並顯示出來

```kotlin
recyclerView.layoutManager = LinearLayoutManager(this)
supportActionBar?.setDisplayHomeAsUpEnabled(true)

val projects: ArrayList<ProjectModel> =
    intent.extras.getParcelableArrayList("projects")
val adapter = CustomAdapter(this, projects)
recyclerView.adapter = adapter
```