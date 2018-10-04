MyLocation
===

這次要在 APP 中顯示 Google Map
目標項目：
- 要求權限
- 取得 Google Map API key
- LocationListener 處理

## 要求權限

在按下按鈕跳轉之前，首先我們要跟使用者要求權限
先檢查是否已經擁有權限，沒有的話再發出一次 Request 
使用者的畫面會跳出確認視窗
```kotlin
fun requestPermission() {
    val result = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION)

    if (result != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                0)
    }else{
        showMaps()
    }
}
```
接著我們要接收使用者的回應，覆寫 onRequestPermissionsResult
因為可以一次 Request 多個權限，permissions 參數裡面會放權限名稱
grantResults 裡面會放多個使用者的回應結果
如果使用者給權限了，才執行跳轉，否則就停留在起始頁
```kotlin
override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

    if (requestCode == 0) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showMaps()
        } else {
            Toast.makeText(this, "需要定位權限", Toast.LENGTH_SHORT).show()
        }
    }
}
```

## 取得 Google Map API key

要使用 Google Map，有內建的工具可以幫助我們快速建立 Fragment
選擇 Gallery 之後，往下拉找到 Google Map

![](https://i.imgur.com/gYZFLls.png)

建立好 Activity 之後，畫面會停留在 google_maps_api.xml
並提示到他提供的網址建立新專案取得 Google Maps API key
取得 API key 之後，我們需要把它取代到 google_maps_api.xml 中底下 YOUR_KEY_HERE 的部分

```xml=
 <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">YOUR_KEY_HERE</string>
```
再來我們回到 MainActivity.kt，建立跳轉頁面的方法
這在要求權限和接收結果時都會用到

```kotlin
fun showMaps() {
    val intent = Intent(this, MapsActivity::class.java)
    startActivity(intent)
}
```

## LocationListener 處理

最後我們開啟工具幫我們建立的 MapsActivity.kt
會看到裡面將基本的方法都建立好了
我們的目標是要顯示現在位置，會需要使用到 LocationManager 這個類別
宣告變數並在 onCreate 時建構
```kotlin
private lateinit var locationManager: LocationManager

override fun onCreate(savedInstanceState: Bundle?) {
    ...
     locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
}
```
下一步使用``` LocationManager.requestLocationUpdates() ```這個方法可以得到 GPS 的最新資訊，該方法基本有 4 個參數
-String provider
-long minTime //表示經過這段時間後更新一次，建議值>=60000毫秒(1分鐘)
-float minDistance //表示經過多少距離更新一次，單位為公尺
-LocationListener listener //偵聽位置資訊，我們需要手動撰寫

```kotlin
val locationListener = object : LocationListener {
    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 12.0f))
            textView.text = "Lat:${location.latitude}\nLon:${location.longitude}"
        }
    }
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    override fun onProviderEnabled(provider: String?) {}
    override fun onProviderDisabled(provider: String?) {}
}
```
最後就可以設定給 requestLocationUpdates 方法
```kotlin
locationManager.requestLocationUpdates(,
            LocationManager.NETWORK_PROVIDER,
            0L,
            0f,
            locationListener
    )
```
