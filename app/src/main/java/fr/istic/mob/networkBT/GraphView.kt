package fr.istic.mob.networkBT

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.*
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
    var bmp: Bitmap? = null
    var mPaint: Paint = Paint()
    var width = 30.0f
    var height = 30.0f
    var graphe: Graph = Graph()
    //var idObjet: Int = 0
    var idConnexion: Int = 0
    var path = Path()

    var status = "default"
    var statusPopUp = "KO"

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
            //return true
        }
        override fun onFling(e1: MotionEvent, e2: MotionEvent,distanceX: Float, distanceY: Float): Boolean
        {
            Log.i("TAG", "onflig ")
            if (status == "connecter_obj") {
                val rect1 = RectF(e1.x, width, e1.y, height)
                val rect2 = RectF(e2.x, width, e2.y, height)
                val obj1 = Objet("obj1", e1.x, e1.y, rect1)
                val obj2 = Objet("obj2", e2.x, e2.y, rect2)
                posx = e1.x
                Log.i("posx", posx.toString())
                posy = e1.y
                Log.i("posy", posy.toString())
                posx2 =e2.x
                Log.i("posx2", posx2.toString())
                posy2 =e2.y
                Log.i("posy2", posy2.toString())


            }
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
        gestureDetector.onTouchEvent(event)
        // if(gestureDetector.onTouchEvent(event)) Comment récupérer un long press ??? et boucler dessus ??

        if (status == "connecter_obj") {
            postInvalidate()
            for (objet in graphe.myObjects.values) {
                if (objet.rec.contains(posx, posy)) {
                    var objet1 = objet
                    for (objet2 in graphe.myObjects.values) {
                        if (objet2.rec.contains(posx2, posy2)) {
                            var cnx = Connexion(objet1, objet2)
                            graphe.myConnexions.put(graphe.myConnexions.size + 1, cnx)
                            Log.i("nb cnx", graphe.myConnexions.toString())
                        }
                    }
                }
                Log.i("oups", "rien trouvé")
                var o1 = Objet("ob1", posx, posy, RectF(posx, width, posy, height))
                var o2 = Objet("ob1", posx2, posy2, RectF(posx2, width, posy2, height))
                var co = Connexion(o1, o2)
                graphe.myConnexions.put(graphe.myConnexions.size + 1, co)
            }
            /*
            if (gestureListener.obj1 != null && gestureListener.obj2!! != null) {
                graphe.addConnexion(
                    this.idConnexion,
                    gestureListener.obj1!!,
                    gestureListener.obj2!!
                )
            }
            idConnexion++
            */

        }
        //Log.e(status, " status en cours")
        return true
    }

    override fun onDraw(canvas: Canvas) {
        //canvas.drawColor(Color.TRANSPARENT)

        for (dessin in graphe.myObjects.values) {
            if(dessin.etiquette != "") {
                canvas.drawCircle(dessin.px, dessin.py, width, mPaint)
            }
        }
        for (connexion in graphe.myConnexions.values) {
            //if (status == "connecter_obj") {
            /*
            var x1 = gestureListener.posx
            var y1 = gestureListener.posy
            var x2 = gestureListener.posx
            var y2 = gestureListener.obj2!!.py
            */
            Log.i("nb cnx",graphe.myConnexions.toString())
            path.moveTo(posx,posy)
            path.lineTo(posx2,posy2)
            //canvas.drawLine(posx1, posy1, posx2, posy2, mPaint)
            canvas.drawPath(path,mPaint)
            //}
        }

    }



    init {
        mPaint.color = Color.CYAN
        mPaint.style = Paint.Style.FILL_AND_STROKE
    }

    fun reinitialize() {
        graphe.Reinitialize()
        postInvalidate()
    }

    fun popup(){
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
            DialogInterface.OnClickListener { dialog, which ->
                //Mettre en conformité le nom entré
                //val p = PointF(posx,posy)
                var rec = RectF(posx,width, posx,height)
                graphe.addObject(input.text.toString(), posx, posy, rec)
                invalidate()
                //Blabla
            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }
    fun addco(x1 :Float, y1: Float, x2:Float, y2:Float){
        if (status == "connecter_obj") {
            postInvalidate()
            for (objet in graphe.myObjects.values){
                if (objet.rec.contains(posx,posy)){
                    var objet1 = objet
                    for (objet2 in graphe.myObjects.values){
                        if (objet2.rec.contains(posx2,posy2)){
                            var cnx = Connexion(objet1,objet2)
                            graphe.myConnexions.put(graphe.myConnexions.size+1,cnx)
                            Log.i("nb cnx",graphe.myConnexions.toString())
                        }
                    }
                }
            }
            /*
            if (gestureListener.obj1 != null && gestureListener.obj2!! != null) {
                graphe.addConnexion(
                    this.idConnexion,
                    gestureListener.obj1!!,
                    gestureListener.obj2!!
                )
            }
            idConnexion++
            */

        }
    }
}