package fr.istic.mob.networkBT

import android.content.Context
import androidx.room.Room

object RoomService {
    lateinit var context: Context
    val appDatabase:NetRoomDatabase by lazy { Room.databaseBuilder(
        context,
        NetRoomDatabase::class.java,
        "moviesdb"
    ).allowMainThreadQueries().build()
    }
}
