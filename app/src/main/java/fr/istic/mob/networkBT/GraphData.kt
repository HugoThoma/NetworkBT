package fr.istic.mob.networkBT

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Graphs")
data class GraphData(
    val nom:String
){
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}
