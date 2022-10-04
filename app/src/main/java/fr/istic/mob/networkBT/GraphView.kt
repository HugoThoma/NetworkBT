package fr.istic.mob.networkBT

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.*
import android.text.InputType
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText


class GraphView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val gestureListener: SimpleGestureListener = SimpleGestureListener()

    var posx = 0f
    var posy = 0f
    var bmp: Bitmap? = null
    var mPaint: Paint = Paint()
    var width = 25.0f
    var height = 50.0f
    var graphe: Graph = Graph()
    var idObjet: Int = 0
    var idConnexion: Int = 0

    var status = "default"

    private var name : String = ""


    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.TRANSPARENT)

        for (dessin in graphe.myObjects.values) {
            if(dessin.etiquette != "") {
            canvas.drawCircle(dessin.px, dessin.py, width, mPaint)
            }
        }
        for (connexion in graphe.myConnexions.values) {
            //if (status == "connecter_obj") {
            var posx1 = gestureListener.obj1!!.px
            var posy1 = gestureListener.obj1!!.py
            var posx2 = gestureListener.obj2!!.px
            var posy2 = gestureListener.obj2!!.py
            canvas.drawLine(posx1, posy1, posx2, posy2, mPaint)
            //}
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //gestureListener
        if (status == "ajouter_obj") {
            //var simpleGestureListener : SimpleGestureListener = SimpleGestureListener()
            posx = event.x
            posy = event.y
            invalidate()
            name = this.popup()
            if(name != ""){
                graphe.addObject(this.idObjet, name, posx, posy)
            }
            name ="" //On remet le nom a vide
            idObjet++
        }
        if (status == "connecter_obj") {
            invalidate()
            if (gestureListener.obj1 != null && gestureListener.obj2!! != null) {
                graphe.addConnexion(
                    this.idConnexion,
                    gestureListener.obj1!!,
                    gestureListener.obj2!!
                )
            }
            idConnexion++
        }
        //Log.e(status, " status en cours")
        return true
    }



    init {
        mPaint.color = Color.CYAN
        mPaint.style = Paint.Style.FILL_AND_STROKE
    }

    fun reinitialize() {
        graphe.Reinitialize()
        postInvalidate()
    }

    fun popup() : String{
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Nommez votre objet !")

        // Set up the input

        // Set up the input
        val input = EditText(context)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.inputType = InputType.TYPE_CLASS_TEXT //or InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setView(input)

        // Set up the buttons

        // Set up the buttons
        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which -> name = input.text.toString() })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
        return name
    }
}