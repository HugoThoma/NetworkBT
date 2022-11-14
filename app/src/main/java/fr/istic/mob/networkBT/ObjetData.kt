package fr.istic.mob.networkBT



import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Objects")

data class ObjetData(
    var etiquette : String,
    var couleur : Int,
    var px : Float,
    var py : Float,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
