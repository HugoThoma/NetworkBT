package fr.istic.mob.networkBT

import android.util.Log

class Graph {
    // contient le descr du graphe des connexions: objet,conx,couleur, posi
  //private var noeuds : HashSet<>

     var myObjects: HashMap<String,Objet> = hashMapOf<String,Objet>()//stock tt les obj
    var myConnexions : HashMap<Int,Connexion> = hashMapOf<Int,Connexion>()//stock tt les obj

     fun addObject(name : String, px: Float, py: Float){
          var objet = Objet(name,px,py)
         myObjects.put(name,objet)
     }
    fun addConnexion(id : Int,ObjetDépart :Objet , ObjetArrivée:Objet){
        var connexion = Connexion(ObjetDépart, ObjetArrivée)
        myConnexions.put(id, connexion)
    }
    fun Reinitialize(){
        myObjects.clear()
        myConnexions.clear()
    }
}