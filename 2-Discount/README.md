Discount
===
**使用 SeekBar 計算折扣**

![](https://i.imgur.com/7MI75R3.gif)

目標項目：
- SeekBar 事件處理
- EditText 事件處理


## SeekBar 事件處理
SeekBar 拉動改變值時會觸發 onProgressChanged 事件，以下設定 setOnSeekBarChangeListener
> 在 Kotlin 中，這邊的 ```object``` 為匿名類別，表示我要傳入的 OnSeekBarChangeListener，且同時覆寫 onProgressChanged；只是將這一串一起寫到 setOnSeekBarChangeListener 的簽名中。
> 
```kotlin
seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            percent = progress
            percentView.text = "打折（$progress%)"
            calculateResult()
        }
})
```
以上也可以寫成
```kotlin
val seekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                percent = progress
                percentView.text = "打折（$progress%)"
                calculateResult()
            }
        }
seekBar.setOnSeekBarChangeListener(seekBarChangeListener)
```

## EditText 事件處理
在 EditText 輸入完數字之後，我們希望在按下確定時能夠更新介面，所以在按下按鈕時去偵測動作
```kotlin
editText.setOnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            calculateResult()
        }
        false
    }
```
>這邊的 setOnEditorActionListener 寫法是更簡潔的 Lambda 寫法
>也可以寫成
```kotlin
editText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            calculateResult()
        }
        return false
    }
})
```
>這邊要注意 onEditorAction 的回傳值，代表是否要隱藏鍵盤。
>
