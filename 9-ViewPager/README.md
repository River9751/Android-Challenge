ViewPager
===

要製作一個可以左右滑動照片的效果
可以使用 ViewPager 來達成

目標項目
- 前置作業
- 自訂 Adapter
- addOnPageChangeListener 處理

## 前置作業
首先要在 MainActivity 加入 ViewPager 的 layout
並給予 id
```xml=
<android.support.v4.view.ViewPager
    android:id="@+id/pager"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    android:layout_marginStart="0dp"
    android:layout_marginTop="0dp"
    android:layout_marginEnd="0dp"
    android:layout_marginBottom="0dp"
    app:layout_constraintBottom_toTopOf="@+id/textView"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
```

MainActivity:
一開始要取得所有照片的 id
這邊有學到一個新方法，加入 drawable 的照片會依照排列給予 id 
所以可以使用 for 迴圈來把照片的 id 都加到 imageList 裡
```kotlin=
fun getImages() {
    imageList = arrayListOf()
    for (i in R.drawable.img01..R.drawable.img06) {
        imageList.add(i)
    }
}
```

```kotlin=
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    getImages()
    textView.text = "1 / ${imageList.size}"

    pager.adapter = CustomAdapter(this, imageList)

    pager.addOnPageChangeListener(
            object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(p0: Int) {}

                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                    val currentPosition = p0 + 1
                    textView.text = "$currentPosition / ${imageList.size}"
                }

                override fun onPageSelected(p0: Int) {}
            })

}
```

## 自訂 Adapter
需要自訂一個 Adapter 來指定給 ViewPager
imageList 拿來存放照片資源檔的 id

覆寫 instantiateItem 可以讓我們把 inflate 出來的 View 加到容器中
```kotlin=

class CustomAdapter : PagerAdapter {

    val context: Context
    val imageList: MutableList<Int>

    constructor(context: Context, imageList: MutableList<Int>) : super() {
        this.context = context
        this.imageList = imageList
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val v = inflater.inflate(R.layout.image_layout, container, false)
        v.imageView.setImageResource(imageList[position])
        container.addView(v)

        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}
```
把 View 的資訊存入 tag 再 print 出來
程式剛啟動的狀況：
```
*** instantiateItem image1
*** instantiateItem image2
*** isViewFromObject View:image1 Any:image1
*** isViewFromObject View:image2 Any:image1
*** isViewFromObject View:image2 Any:image2
*** isViewFromObject View:image1 Any:image1
*** isViewFromObject View:image2 Any:image1
*** isViewFromObject View:image2 Any:image2
*** isViewFromObject View:image1 Any:image1
*** isViewFromObject View:image2 Any:image1
*** isViewFromObject View:image2 Any:image2
```

移動到第四張時：
```
*** destroyItem image1
*** instantiateItem image4
*** isViewFromObject View:image4 Any:image2
*** isViewFromObject View:image4 Any:image3
*** isViewFromObject View:image4 Any:image4
*** isViewFromObject View:image2 Any:image2
*** isViewFromObject View:image3 Any:image2
*** isViewFromObject View:image3 Any:image3
*** isViewFromObject View:image4 Any:image2
*** isViewFromObject View:image4 Any:image3
*** isViewFromObject View:image4 Any:image4
```
測試發現 ViewPager 只會保留當前內容和前後一張的內容
但不清楚為什麼 isViewFromObject 會判斷這麼多次