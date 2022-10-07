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
    var Trait : Paint = Paint()
    var width = 30.0f
    var height = 30.0f
    var graphe: Graph = Graph()
    //var idObjet: Int = 0
    var idConnexion: Int = 0
    var path = Path()
    var temp = Path()
    var objet1 : Objet? = null
    var objet2 : Objet? = null

    var status = "default"

    private var name : String = ""

    var gestureDetector: GestureDetector = GestureDetector(context, object:SimpleOnGestureListener(){

        override fun onDown(e: MotionEvent): Boolean {
            return true // nécessaire !!!
        }

        override fun onLongPress(event: MotionEvent) {

            super.onLongPress(event)
            if (status == "ajouter_obj") {

                posx = event.x
                posy = event.y
                popup()
                name ="" //On remet le nom a vide
            }
        }
        override fun onFling(e1: MotionEvent, e2: MotionEvent,distanceX: Float, distanceY: Float): Boolean
        {
            //addco(posx,posy,posx2,posy2)
            return super.onFling(e1, e2, distanceX, distanceY)
        }
        /*
        override fun onTouchEvent(event: MotionEvent): Boolean {
    when (event.action) {
        MotionEvent.ACTION_DOWN -> {
            startedDrawing = true
            startPoint.x = event.x
            startPoint.y = event.y
            currentPoint.x = event.x
            currentPoint.y = event.y
            invalidate()
        }
        MotionEvent.ACTION_MOVE -> {
            currentPoint.x = event.x
            currentPoint.y = event.y
            invalidate()
        }
        MotionEvent.ACTION_UP -> {
            startedDrawing = false
            invalidate()
        }
    }
    return true
}
    boolean drawRectangle = false;
   PointF beginCoordinate;
   PointF endCoordinate;

   public boolean onTouch(View v, MotionEvent event) {
      switch(event.getAction()) {
         case MotionEvent.ACTION_DOWN:
            drawRectangle = true; // Start drawing the rectangle
            beginCoordinate.x = event.getX();
            beginCoordinate.y = event.getY();
            endCoordinate.x = event.getX();
            endCoordinate.y = event.getY();
            invalidate(); // Tell View that the canvas needs to be redrawn
            break;
         case MotionEvent.ACTION_MOVE:
            endCoordinate.x = event.getX();
            endCoordinate.y = event.getY();
            invalidate(); // Tell View that the canvas needs to be redrawn
            break;
         case MotionEvent.ACTION_UP:
            // Do something with the beginCoordinate and endCoordinate, like creating the 'final' object
            drawRectangle = false; // Stop drawing the rectangle
            invalidate(); // Tell View that the canvas needs to be redrawn
            break;
      }
      return true;
   }

   protected void onDraw(Canvas canvas) {
      if(drawRectangle) {
         // Note: I assume you have the paint object defined in your class
         canvas.drawRect(beginCoordinate.x, beginCoordinate.y, endCoordinate.x, endCoordinate.y, paint);
      }
   }
}
*/
    })

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if(status == "connecter_obj") {
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    objet1 = ObjetProche(event.x, event.y)
                    if (objet1 !== null ){
                        temp.moveTo(objet1!!.px, objet1!!.py)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    objet2 = ObjetProche(event.x, event.y)
                    if (objet2 != null && objet1 != objet2 && objet1 != null){
                        var nameCo = popup()
                        var cnx = Connexion(nameCo.toString(),objet1!!,objet2!!)
                        graphe.myConnexions.put(nameCo.toString(), cnx)
                    }
                    temp.reset()
                }
                MotionEvent.ACTION_MOVE -> {
                    if (objet1 != null ){
                        temp.lineTo(event.x, event.y)
                    }

                }
            }
        }
        invalidate()
        return gestureDetector.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        for (dessin in graphe.myObjects.values) {
            if(dessin.etiquette != "") {
                canvas.drawCircle(dessin.px, dessin.py, width, mPaint)
                canvas.drawText(dessin.etiquette, dessin.px, dessin.py, TxtPaint)
            }
        }
        for (connexion in graphe.myConnexions.values) {
            Log.i("nb cnx",graphe.myConnexions.toString())
            path.moveTo(connexion.objet1.px,connexion.objet1.py)
            path.lineTo(connexion.objet2.px,connexion.objet2.py)
            //canvas.drawLine(connexion.objet1.px, connexion.objet1.py, connexion.objet2.px, connexion.objet2.py, Trait)
            canvas.drawPath(path,Trait)
            canvas.drawText(connexion.name, (connexion.objet1.px+connexion.objet2.px)/2, (connexion.objet1.py+connexion.objet2.py)/2, TxtPaint)
            path.reset()
        }
        canvas.drawPath(temp,Trait)
    }



    init {
        val spSize = 12
        val TxtSize = spSize * resources.displayMetrics.scaledDensity
        mPaint.color = Color.CYAN
        mPaint.style = Paint.Style.FILL_AND_STROKE
        TxtPaint.color = Color.BLACK
        TxtPaint.style = Paint.Style.FILL_AND_STROKE
        TxtPaint.setTextSize(TxtSize)
        Trait.strokeWidth= 12f
        Trait.color = Color.GREEN
        Trait.style = Paint.Style.STROKE
    }

    fun reinitialize() {
        graphe.Reinitialize()
        postInvalidate()
    }

    fun ObjetProche(px:Float,py:Float): Objet? {
        for (objet in graphe.myObjects.values) {
            if (objet.px < px + 25 && objet.px > px -25){
                if (objet.py < py + 25 && objet.py > py -25){
                    Log.e("Trouvé", "objet1")
                    return objet
                }
            }
        }
        return null
    }

    fun popup(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Donnez un nom !")

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
                var rec = RectF(posx,width, posx,height)
                graphe.addObject(input.text.toString(), posx, posy, rec)
                invalidate()
            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }
}