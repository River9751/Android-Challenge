Alarm(DatePickerDialog, TimePickerDialog)
===

![](https://i.imgur.com/g5QdLbj.gif)

學習使用內建的 Dialog 來選擇時間和日期
首先在 MainActivity 中宣告
```kotlin
private lateinit var calendar: Calendar
```
接著在 onCreate 的時候建構 calendar 和 設定按鈕事件
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    calendar = Calendar.getInstance()

    tvDate.setOnClickListener { dateClick() }
    tvTime.setOnClickListener { timeClick() }
    button.setOnClickListener { buttonClick() }
}
```
選擇日期按鈕，傳入的參數需要 context, OnDateSetListener 和初始化的年月日
這裡使用 calendar.get 來取得現在的年月日
OnDateSetListener 是選擇完日期確認後觸發的事件
SimpleDateFormat 定義一個格式，後續就可以使用 SimpleDateFormat.format 來轉換時間的顯示方式

```kotlin
private fun dateClick() {
    DatePickerDialog(
            this,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DATE)
    ).show()
}

private val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, date ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DATE, date)

        val d = SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN)
        tvDate.text = d.format(calendar.time)
    }
```

## 選擇時間按鈕

TimePickerDialog 的最後一個參數表示是否顯示 24 小時制

```kotlin
private fun timeClick() {
    TimePickerDialog(
            this,
            timeSetListener,
            calendar.get(Calendar.HOUR),
            calendar.get(Calendar.MINUTE),
            true
    ).show()
}

private val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hour, minute ->

    calendar.set(Calendar.HOUR, hour)
    calendar.set(Calendar.MINUTE, minute)

    val t = SimpleDateFormat("HH:mm", Locale.TAIWAN)
    tvTime.text = t.format(calendar.time)
}

```

## AlertDialog

最後 Go 的按鈕按下時，跳出一個 AlertDialog 顯示資訊
按鈕事件中的 which 參數會得到按鈕的 id 
最後 builder create 完成後記得 .show() 才會顯示在畫面上
```kotlin
private fun buttonClick() {
    val time =
            SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.TAIWAN)
                    .format(calendar.time)

    val builder = AlertDialog.Builder(this)

    builder.setPositiveButton("OK") { dialog, which ->
        //
    }

    builder.setNegativeButton("Cancel") { dialog, which ->
        //
    }

    builder
            .setTitle("Title")
            .setMessage("$time")
            .create()
            .show()
}
```