ImagePicker
===
**設定兩個按鈕，分別能夠顯示相簿裡的圖片和直接拍照的圖片**

![](https://i.imgur.com/UL97Uo2.gif)

目標項目：
- 傳送不同的 intent 設置
- onActivityResult 接收結果的處理

## 傳送不同的 intent 設置
設定 intent ， Android 系統會自動幫我們呼叫出可以處理的 App

相簿 intent： ACTION_PICK 表示要選擇裝置裡面的檔案
而 intent.type 有點像是過濾器，只顯示 image 類

呼叫相機 intent：
帶入 MediaStore.ACTION_IMAGE_CAPTURE 來呼叫相機

最後使用 startActivityForResult() 把 intent 和自定義的 request code 帶過去。
```kotlin
//相簿
album.setOnClickListener {
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
    startActivityForResult(intent, ACTION_ALBUM_REQUEST_CODE)
}
//相機
camera.setOnClickListener {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(intent, ACTION_CAMERA_REQUEST_CODE)
}
```
接著要覆寫 onActivityResult 以取得回傳回來的資料
從相機 App 回傳回來的圖會存放在 data 中，使用 data.extras.get("data") 取得，但要記得轉型
相簿回傳的資料，需要一個 Content Resolver 來處理他
最後 bitmap 資料會使用 MediaStore.Images.Media.getBitmap() 來取得
```kotlin
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            ACTION_CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    imageView.setImageBitmap(data.extras.get("data") as Bitmap)
                }
            }
            ACTION_ALBUM_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val resolver = this.contentResolver
                    val bitmap =
                            MediaStore.Images.Media.getBitmap(resolver, data?.data)
                    imageView.setImageBitmap(bitmap)
                }
            }
            else -> {
                println("no correspond resultCode found")
            }
        }
    }
```
>ACTION_CAMERA_REQUEST_CODE 和 ACTION_ALBUM_REQUEST_CODE 是自定義的變數，型別為 Int，用來作為呼叫對象的區別。