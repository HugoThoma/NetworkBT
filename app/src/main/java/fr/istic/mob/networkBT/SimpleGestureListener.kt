package fr.istic.mob.networkBT

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.PointF
import android.util.Log
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent

public class SimpleGestureListener : SimpleOnGestureListener() {

    var posx = 0f
    var posy = 0f
    var obj1 : Objet?=null
    var obj2 : Objet?=null


    override fun onDown(e: MotionEvent): Boolean {
        return true // nécessaire !!!
    }

    override fun onLongPress(e: MotionEvent) {

        super.onLongPress(e)
        Log.e("longPress", "------------------------------------------")
        //return true
    }
    override fun onFling(e1: MotionEvent, e2: MotionEvent,distanceX: Float, distanceY: Float): Boolean
    {
        val p = PointF(e1.x, e1.y)
        val p1 = PointF(e2.x, e2.y)
         obj1 = Objet("obj1",e1.x,e1.y, p)
         obj2 = Objet("obj2",e2.x,e2.y, p1)

        return super.onFling(e1, e2, distanceX, distanceY)
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        return super.onDoubleTap(e)
    }
}