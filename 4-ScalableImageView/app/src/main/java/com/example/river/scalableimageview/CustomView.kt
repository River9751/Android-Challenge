package com.example.river.scalableimageview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView

class CustomView : ImageView {

    private var downX = 0f
    private var downY = 0f
    private var offsetX = 0
    private var offsetY = 0

    private var mode = Mode.None
    private var oriDistance = 0.0

//    constructor(context: Context?) : super(context)

    enum class Mode {
        Zoom,
        Drag,
        None
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        performClick()

        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                mode = Mode.Drag
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                //println("*** ACTION_POINTER_DOWN")
                oriDistance = getDistance(
                        event.getX(0),
                        event.getY(0),
                        event.getX(1),
                        event.getY(1)
                )
                mode = Mode.Zoom
            }
            MotionEvent.ACTION_MOVE -> {
                when (mode) {
                    Mode.Drag -> {
                        if (mode == Mode.Drag) {
                            //先求出偏移量(此時 View 還沒有移動位置)
                            offsetX = (event.x - downX).toInt()
                            offsetY = (event.y - downY).toInt()
                            setFrame(
                                    this.left + offsetX,
                                    this.top + offsetY,
                                    this.right + offsetX,
                                    this.bottom + offsetY
                            )
                        }
                    }
                    Mode.Zoom -> {
                        val x1 = event.getX(0)
                        val y1 = event.getY(0)
                        val x2 = event.getX(1)
                        val y2 = event.getY(1)

                        println("$x1, $y1, $x2, $y2")
                        var distance = getDistance(x1, x2, y1, y2)

                        var ratio = distance / oriDistance
                        scaleImage(ratio)
                        //在縮放完圖片之後，需把移動後的手指距離存為原始距離
                        //不然假設手指間的距離變成 3 倍
                        //但是縮放卻不會是從原始大小開始縮放
                        //而是從上一次縮放完的結果開始
                        this.oriDistance = distance
                    }
                }

            }
            MotionEvent.ACTION_POINTER_UP -> {
                mode = Mode.None
            }
            MotionEvent.ACTION_UP -> {
                mode = Mode.None
            }
        }
        return true
    }

    fun getDistance(x1: Float, y1: Float, x2: Float, y2: Float): Double {
        return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)).toDouble())
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    fun scaleImage(ratio: Double) {

        if (ratio > 1) {
            val increasedWidth = this.width * (ratio - 1)
            val increasedHeight = this.height * (ratio - 1)

            val left = this.left - (increasedWidth / 2)
            val right = this.right + (increasedWidth / 2)
            val top = this.top - (increasedHeight / 2)
            val bottom = this.bottom + (increasedHeight / 2)

            setFrame(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())

        } else if (ratio < 1) {
            val decreasedWidth = this.width * (1 - ratio)
            val decreasedHeight = this.height * (1 - ratio)

            val left = this.left + (decreasedWidth / 2)
            val right = this.right - (decreasedWidth / 2)
            val top = this.top + (decreasedHeight / 2)
            val bottom = this.bottom - (decreasedHeight / 2)

            setFrame(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        }
    }
}