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

    val gestureListener : SimpleGestureListener = SimpleGestureListener()

    var posx = 0f
    var posy = 0f
    var bmp: Bitmap? = null
    var mPaint: Paint
    var width = 25.0f
    var height = 50.0f
    var graphe: Graph = Graph()
    var idObjet: Int = 0
    var idConnexion: Int = 0

    var status = ""


    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.TRANSPARENT)
        /*if (touched) {
            canvas.drawRect(posx, posy, posx + width, posy + height, mPaint)
            postInvalidate()
        }*/

            for (dessin in graphe.myObjects.values) {
                if(status == "ajouter_obj") {
                    canvas.drawCircle(dessin.px, dessin.py, width, mPaint)
                }


            }
            for (connexion in graphe.myConnexions.values){
                if (status == "connecter_obj") {
                    var posx1 = gestureListener.obj1!!.px
                    var posy1 = gestureListener.obj1!!.py
                    var posx2 = gestureListener.obj2!!.px
                    var posy2 = gestureListener.obj2!!.py
                    canvas.drawLine(posx1, posy1, posx2, posy2, mPaint)
                }
            }
        /*  if(status == "connecter_obj"){
                    canvas.drawLine(float startX, float startY, float stopX, float stopY, mPaint);
                }*/



    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureListener
        if (status == "ajouter_obj") {
            //var simpleGestureListener : SimpleGestureListener = SimpleGestureListener()
            posx = event.x
            posy = event.y
            invalidate()
            graphe.addObject(this.idObjet, "nom", posx, posy)
            idObjet++
        }

        if(status == "connecter_obj"){

            invalidate()
            if(gestureListener.obj1 != null && gestureListener.obj2!! != null){
                graphe.addConnexion(this.idConnexion,gestureListener.obj1!!,gestureListener.obj2!!)
            }


            idConnexion++
        }
        return true
    }

    init {
        mPaint = Paint()
        mPaint.color = Color.CYAN
        mPaint.style = Paint.Style.FILL_AND_STROKE
    }
}