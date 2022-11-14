package fr.istic.mob.networkBT

import androidx.room.*

@Dao
interface ObjetDAO {
    @Insert
    fun addObjet(objet: ObjetData):Long

    @Query("SELECT * FROM Objects")
    fun getAllObjects():List<ObjetData>

    @Query("DELETE FROM Objects")
    fun deleteAllObjects()
}


