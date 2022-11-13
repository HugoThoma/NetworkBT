package fr.istic.mob.networkBT

import android.app.Application

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        RoomService.context = applicationContext
    }
}
