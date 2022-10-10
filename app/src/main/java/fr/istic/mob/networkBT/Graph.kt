package fr.istic.mob.networkBT

import android.graphics.Color
import android.graphics.PointF
import android.graphics.RectF
import android.util.Log
import java.util.*
import kotlin.collections.HashMap

class Graph {
    // contient le descr du graphe des connexions: objet,conx,couleur, posi
  //private var noeuds : HashSet<>
    var colorofobject = Color.CYAN
    var colorofpath = Color.GREEN
    var strokeWidth = 12f
    var myObjects: HashMap<String,Objet> = hashMapOf<String,Objet>()//stock tt les obj
    var myConnexions : HashMap<String,Connexion> = hashMapOf<String,Connexion>()//stock tt les obj

     fun addObject(name : String, px: Float, py: Float){
          var objet = Objet(name,px,py)
         myObjects.put(name,objet)
     }
    fun addConnexion(name : String,ObjetDépart :Objet , ObjetArrivée:Objet){
        var connexion = Connexion(name, ObjetDépart, ObjetArrivée)
        myConnexions.put(name, connexion)
    }
    fun Reinitialize(){
        myObjects.clear()
        myConnexions.clear()
    }
}