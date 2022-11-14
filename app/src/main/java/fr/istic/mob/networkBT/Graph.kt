package fr.istic.mob.networkBT

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.Log
import java.util.*
import kotlin.collections.HashMap

class Graph {
    // contient le descr du graphe des connexions: objet,conx,couleur, posi
    var strokeWidth = 12f
    var myObjects: HashMap<String, Objet> = hashMapOf<String, Objet>()//stock tt les obj
    var myConnexions: HashMap<String, Connexion> = hashMapOf<String, Connexion>()//stock tt les obj
// color was Paint
    fun addObject(name: String, color: Paint, image: String, px: Float, py: Float) {
            var objet = Objet(name, color, image, px, py)
            myObjects.put(name, objet)
    }

    fun addConnexion(
        name: String,
        color: Paint,
        epaisseur: Float,
        ObjetDépart: Objet,
        ObjetArrivée: Objet,
        px_nom: Float,
        py_nom: Float
    ) {
        var connexion = Connexion(name, color, epaisseur, ObjetDépart, ObjetArrivée, px_nom, py_nom)
        myConnexions.put(name, connexion)
    }

    fun Reinitialize() {
        myObjects.clear()
        myConnexions.clear()
    }
//color was paint
    fun setObjet(oldName: String, name: String, color: Paint, image: String, px: Float, py: Float) {
        deleteObjet(oldName)
        addObject(name,color,image,px,py)
    }

    fun setConnexion(oldName: String, name : String, color : Paint,epaisseur: Float, objetDépart: Objet, objetArrivée: Objet, px_nom: Float, py_nom: Float){
        deleteConnexion(oldName)
        addConnexion(name, color, epaisseur, objetDépart, objetArrivée, px_nom, py_nom)
    }

    //Pour donner des coordonnées au libellé de la connexion
    fun setConnexionName_coord(name: String, Objet1: Objet, Objet2: Objet) {
        for (connexion in myConnexions.values) {
            if (connexion.name == name) {
                connexion.px_nom = (connexion.objet1.px + connexion.objet2.px) / 2
                connexion.py_nom = (connexion.objet1.py + connexion.objet2.py) / 2
            }
        }
    }

    fun deleteObjet(name: String){
        val iterator = myObjects.iterator()
        while (iterator.hasNext()){
            val item = iterator.next()
            if(item.value.etiquette == name){
                iterator.remove()
            }
        }
        for (connexion in myConnexions.values) {
            if(connexion.objet1.etiquette == name || connexion.objet2.etiquette == name){
                deleteConnexion(connexion.name)
            }
        }
    }

    fun deleteConnexion(name: String){
        val iterator = myConnexions.iterator()
        while (iterator.hasNext()){
            val item = iterator.next()
            if(item.value.name == name){
                iterator.remove()
            }
        }
    }
}