package fr.istic.mob.networkBT

import android.graphics.Bitmap
import android.graphics.Paint
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent

internal class SimpleGestureListener : SimpleOnGestureListener() {

    var posx= 0f
    var posy = 0f

    override fun onDown(event: MotionEvent): Boolean {
        // triggers first for both single tap and long press
        return true
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        // triggers after onDown only for single tap
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        // triggers after onDown only for long press
        super.onLongPress(event)
        //getting the touched x and y position

    }
}