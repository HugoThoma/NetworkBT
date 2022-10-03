package fr.istic.mob.networkBT

class Graph {
    // contient le descr du graphe des connexions: objet,conx,couleur, posi
  //private var noeuds : HashSet<>

     var myObjects: HashMap<Int,Objet> = hashMapOf<Int,Objet>()//stock tt les obj
    var myConnexions : HashMap<Int,Connexion> = hashMapOf<Int,Connexion>()//stock tt les obj

     fun addObject(id : Int ,name : String, px: Float, py: Float){
          var objet = Objet(name,px,py)
         myObjects.put(id,objet)
     }
    fun addConnexion(id : Int,ObjetDépart :Objet , ObjetArrivée:Objet){
        var connexion = Connexion(ObjetDépart, ObjetArrivée)
        myConnexions.put(id, connexion)
    }
}