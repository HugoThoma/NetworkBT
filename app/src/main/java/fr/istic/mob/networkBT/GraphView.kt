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
    var graphe : Graph = Graph()
    var idObjet : Int = 0

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.TRANSPARENT)
        /*if (touched) {
            canvas.drawRect(posx, posy, posx + width, posy + height, mPaint)
            postInvalidate()
        }*/
        for (dessin in graphe.mygraph.values){
            canvas.drawCircle(dessin.px, dessin.py, width, mPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //var simpleGestureListener : SimpleGestureListener = SimpleGestureListener()
        touched = true
        posx = event.x
        posy = event.y
        invalidate()

        graphe.addObject(this.idObjet,"nom",posx,posx)
        idObjet++
        return true
    }

    init {
        mPaint = Paint()
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
    }
}