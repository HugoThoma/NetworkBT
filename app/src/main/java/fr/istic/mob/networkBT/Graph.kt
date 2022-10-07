package fr.istic.mob.networkBT

import android.graphics.PointF
import android.graphics.RectF
import android.util.Log

class Graph {
    // contient le descr du graphe des connexions: objet,conx,couleur, posi
  //private var noeuds : HashSet<>

     var myObjects: HashMap<String,Objet> = hashMapOf<String,Objet>()//stock tt les obj
    var myConnexions : HashMap<String,Connexion> = hashMapOf<String,Connexion>()//stock tt les obj

     fun addObject(name : String, px: Float, py: Float, p: RectF){
          var objet = Objet(name,px,py,p)
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