package fr.istic.mob.networkBT

import androidx.room.*

@Dao
interface ConnexionDAO {

    @Insert
    fun addConnexionData(connexionData: ConnexionData):Long

    @Query("SELECT * FROM ConnexionDatas")
    fun getAllConnexionDatas():List<ConnexionData>

    @Query("DELETE FROM ConnexionDatas")
    fun deleteAllConnexionDatas()

}