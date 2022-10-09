package fr.istic.mob.networkBT

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.*
import android.graphics.Paint
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.EditText


class GraphView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val gestureListener: SimpleGestureListener = SimpleGestureListener()

    var posx = 0f
    var posy = 0f
    var posx2 = 0f
    var posy2 = 0f
    var mPaint: Paint = Paint()
    var TxtPaint: Paint = Paint()
    var Trait: Paint = Paint()
    var width = 30.0f
    var height = 30.0f
    var graphe: Graph = Graph()

    //var idObjet: Int = 0
    var idConnexion: Int = 0
    var path = Path()
    var temp = Path()
    var objet1: Objet? = null
    var objet2: Objet? = null
    var objetModif: Objet? = null

    var status = "default"

    private var name: String = ""

    var gestureDetector: GestureDetector =
        GestureDetector(context, object : SimpleOnGestureListener() {

            override fun onDown(e: MotionEvent): Boolean {
                return true // nécessaire !!!
            }

            override fun onLongPress(event: MotionEvent) {

                super.onLongPress(event)
                if (status == "ajouter_obj") {

                    posx = event.x
                    posy = event.y
                    popup()
                    name = "" //On remet le nom a vide
                }
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                //addco(posx,posy,posx2,posy2)
                return super.onFling(e1, e2, distanceX, distanceY)
            }

            override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
                if (status=="modifier_obj"){
                    if (e != null) {
                        objetModif = ObjetProche(e.x, e.y)
                    }
                    if (objet1 !== null) {
                        popup()
                    }
                }
                    return super.onDoubleTapEvent(e)
                }
        })

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (status == "connecter_obj") {
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    objet1 = ObjetProche(event.x, event.y)
                    if (objet1 !== null) {
                        temp.moveTo(objet1!!.px, objet1!!.py)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    objet2 = ObjetProche(event.x, event.y)
                    if (objet2 != null && objet1 != objet2 && objet1 != null) {
                        var nameCo = popup()

                    }
                    temp.reset()
                }
                MotionEvent.ACTION_MOVE -> {
                    if (objet1 != null) {
                        temp.lineTo(event.x, event.y)
                    }
                }
            }
        }

        if (status == "modifier_obj") {
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    objetModif = ObjetProche(event.x, event.y)
                }
                MotionEvent.ACTION_UP -> {
                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    if (objetModif != null) {
                        //Actualiser les coordonnées de l'objet
                        var nomObj = objetModif!!.etiquette.toString()
                        objetModif!!.px = event.x
                        objetModif!!.py = event.y
                        graphe.myObjects.set(nomObj, objetModif!!)
                    }
                }
            }
        }

        invalidate()
        return gestureDetector.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        for (dessin in graphe.myObjects.values) {
            if (dessin.etiquette != "") {
                canvas.drawCircle(dessin.px, dessin.py, width, mPaint)
                canvas.drawText(dessin.etiquette, dessin.px, dessin.py, TxtPaint)
            }
        }
        for (connexion in graphe.myConnexions.values) {
            Log.i("nb cnx", graphe.myConnexions.toString())
            path.moveTo(connexion.objet1.px, connexion.objet1.py)
            path.lineTo(connexion.objet2.px, connexion.objet2.py)
            //canvas.drawLine(connexion.objet1.px, connexion.objet1.py, connexion.objet2.px, connexion.objet2.py, Trait)
            canvas.drawPath(path, Trait)
            canvas.drawText(
                connexion.name,
                (connexion.objet1.px + connexion.objet2.px) / 2,
                (connexion.objet1.py + connexion.objet2.py) / 2,
                TxtPaint
            )
            path.reset()
        }
        canvas.drawPath(temp, Trait)
    }


    init {
        val spSize = 12
        val TxtSize = spSize * resources.displayMetrics.scaledDensity
        mPaint.color = Color.CYAN
        mPaint.style = Paint.Style.FILL_AND_STROKE
        TxtPaint.color = Color.BLACK
        TxtPaint.style = Paint.Style.FILL_AND_STROKE
        TxtPaint.textSize = TxtSize
        Trait.strokeWidth = 12f
        Trait.color = Color.GREEN
        Trait.style = Paint.Style.STROKE
    }

    fun reinitialize() {
        graphe.Reinitialize()
        postInvalidate()
    }

    fun ObjetProche(px: Float, py: Float): Objet? {
        for (objet in graphe.myObjects.values) {
            if (objet.px < px + 25 && objet.px > px - 25) {
                if (objet.py < py + 25 && objet.py > py - 25) {
                    Log.e("Trouvé", "objet1")
                    return objet
                }
            }
        }
        return null
    }

    fun popup() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(resources.getString(R.string.PopUp))


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
            DialogInterface.OnClickListener { dialog, which ->
                //Mettre en conformité le nom entré
                //val p = PointF(posx,posy)
                var rec = RectF(posx, width, posx, height)
                if(status == "ajouter_obj") {
                    graphe.addObject(input.text.toString(), posx, posy, rec)
                } else if(status == "connecter_obj"){
                    var cnx = Connexion(input.text.toString(), objet1!!, objet2!!)
                    graphe.myConnexions.put(input.text.toString(), cnx)
                } /*else if(status == "modifier_obj"){
                    for (connexion in graphe.myConnexions.values) {
                        if(connexion.objet1 == objetModif){
                            var nameCo = connexion.name
                            var objTempo = connexion.objet2
                            graphe.myConnexions.remove(nameCo)
                            var newCnx = Connexion(nameCo, objetModif!!, objTempo)
                            graphe.myConnexions.put(nameCo, newCnx)
                        } else if(connexion.objet2 == objetModif){
                            var nameCo = connexion.name
                            var objTempo = connexion.objet1
                            graphe.myConnexions.remove(nameCo)
                            var newCnx = Connexion(nameCo, objTempo,objetModif!!)
                            graphe.myConnexions.put(nameCo, newCnx)
                        }
                    }
                    graphe.myObjects.remove(objetModif!!.etiquette)
                    graphe.addObject(input.text.toString(),objetModif!!.px, objetModif!!.py, rec)
                }*/
                invalidate() //blabla
            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }
}