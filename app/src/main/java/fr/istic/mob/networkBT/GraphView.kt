package fr.istic.mob.networkBT

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class GraphView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var posx= 0f
    var posy = 0f
    var bmp: Bitmap? = null
    var mPaint: Paint
    var width = 50.0f
    var height = 50.0f
    var touched = false
    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.TRANSPARENT)
        if (touched) {
            canvas.drawRect(posx, posy, posx + width, posy + height, mPaint)
            postInvalidate()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        touched = true
        //getting the touched x and y position
        posx = event.x
        posy = event.y
        invalidate()
        return true
    }

    init {
        mPaint = Paint()
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
    }
}