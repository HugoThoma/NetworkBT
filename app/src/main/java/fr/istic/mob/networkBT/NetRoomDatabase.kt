package fr.istic.mob.networkBT

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ConnexionData::class, ObjetData::class], version = 1, exportSchema = false)
public abstract class NetRoomDatabase : RoomDatabase() {

    abstract fun getConnexionDao(): ConnexionDAO
    abstract fun getObjetDao(): ObjetDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NetRoomDatabase? = null

        fun getDatabase(context: Context): NetRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NetRoomDatabase::class.java,
                    "network_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
