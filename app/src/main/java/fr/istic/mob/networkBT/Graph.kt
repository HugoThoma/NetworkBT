package fr.istic.mob.networkBT

class Graph {
    // contient le descr du graphe des connexions: objet,conx,couleur, posi
  //private var noeuds : HashSet<>

     var mygraph : HashMap<Int,Objet> = hashMapOf<Int,Objet>()
     fun addObject(id : Int ,name : String, px: Float, py: Float){
          var objet = Objet(name,px,py)
         mygraph.put(id,objet)
     }
}