package fr.istic.mob.networkBT

import android.graphics.Paint

data class Connexion(
    var name: String,
    var color: Paint,
    var epaisseur: Float,
    var objet1: Objet,
    var objet2: Objet,
    var px_nom: Float,
    var py_nom:Float,

    )
