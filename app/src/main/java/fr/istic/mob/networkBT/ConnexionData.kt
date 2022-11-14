package fr.istic.mob.networkBT

import android.graphics.Paint
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ConnexionDatas",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = ObjetData::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idObjet1"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ObjetData::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idObjet2"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class ConnexionData(
    var name: String,
    var color: Int,
    var epaisseur: Float,
    var px_nom: Float,
    var py_nom:Float,
    var idObjet1: Long,
    var idObjet2: Long
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}
