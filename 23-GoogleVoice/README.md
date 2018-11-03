Day 25 GoogleVoice
=== 

今天學習如何使用 Google 的語音辨識功能

![](https://i.imgur.com/zJeVuYp.gif)

流程為按下按鈕後先 reset 三個圖形的顏色
然後 Intent 到語音辨識的 Activity

## Intent
按下按鈕後設定 Intent 內容
- RecognizerIntent.ACTION_RECOGNIZE_SPEECH 為這次 Intent 的 action
- EXTRA_PROMPT 設定語音辨識畫面要顯示的標題
- EXTRA_LANGUAGE 設定語言
- EXTRA_MAX_RESULTS 為返回的結果數，最符合的會放在第一個結果

然後使用 startActivityForResult 啟動

```kotlin
button.setOnClickListener {
    resetColor()

    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening...")
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-TW")
    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)

    startActivityForResult(intent, requestCode)
}
```

## onActivityResult
為了接收語音辨識的結果，覆寫 onActivityResult 方法
從回傳的 ArrayList 中取出第一個結果，並顯示在畫面上
然後依據得到的文字變更圖案的顏色

```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    if (requestCode == requestCode && resultCode == Activity.RESULT_OK) {
        val matches = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        val text = matches.first()
        textView.text = text
        changeColor(text)
    }

    super.onActivityResult(requestCode, resultCode, data)
}

```

## 變更圖案顏色

把語音辨識的文字傳進來，再依照得到的文字使用 setColorFilter 方法變更 vector 的顏色就完成了

```kotlin
fun changeColor(text: String) {
    when {
        text.contains("三角形") -> triangle.setColorFilter(Color.YELLOW)
        text.contains("圓形") -> circle.setColorFilter(Color.YELLOW)
        text.contains("正方形") -> square.setColorFilter(Color.YELLOW)
        else -> resetColor()
    }
}
```
> 開發時有遇到一個問題 
> No Activity found to handle Intent { act=android.speech.action.RECOGNIZE_SPEECH (has extras) }
> 原因是因為把 Google App 給停用了，所以無法使用語音辨識功能，將它開啟即可