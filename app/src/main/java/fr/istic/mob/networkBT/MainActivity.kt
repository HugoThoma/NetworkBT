package fr.istic.mob.networkBT

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.view.menu.MenuBuilder


class MainActivity : AppCompatActivity() {


    lateinit var graphView: GraphView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        graphView = findViewById(R.id.view)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        // Pour la suite au cas ou : If you want Icon display in Overflow Menu.
        // https://stackoverflow.com/questions/19750635/icon-in-menu-not-showing-in-android
        if (menu is MenuBuilder) {
            val m: MenuBuilder = menu
            m.setOptionalIconsVisible(true)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) { //Pour chaque ID d'élément du menu reçu ...
            R.id.objet -> {
                graphView.status = "ajouter_obj"
                return true
            }
            R.id.connecter -> {
                graphView.status = "connecter_obj"
                return true
            }
            R.id.modifierobjet -> {
                graphView.status = "modifier_obj"
                return true
            }
            R.id.modifierconnexion -> {
                graphView.status = "modifier_cnx"
                return true
            }
            R.id.reini -> {
                Log.e("Status", "reinitialize")
                graphView.reinitialize()
                graphView.status = "default"
                //graphView.status = "reinitialize"
                return true
            }
            R.id.sauver -> {
                graphView.status = "sauv"
                return true
            }
            R.id.load -> {
                graphView.status = "load"
                return true
            }
            R.id.plan -> {
                graphView.status = "plan"
                return true
            }
            R.id.mail -> {
                graphView.status = "mail"
                return true
            }
            R.id.plan1 -> {
                graphView.setBackgroundResource(R.drawable.plan1)
                return true
            }
            R.id.plan2 -> {
                graphView.setBackgroundResource(R.drawable.plan2)
                return true
            }

        }

        return true
    }
}