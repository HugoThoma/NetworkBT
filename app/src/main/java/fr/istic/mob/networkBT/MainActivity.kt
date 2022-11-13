package fr.istic.mob.networkBT

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.content.FileProvider
import androidx.room.Room
import java.io.*


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
                save()
                return true
            }
            R.id.load -> {
                graphView.status = "load"
                load()
                return true
            }
            R.id.plan -> {
                graphView.status = "plan"
                return true
            }
            R.id.mail -> {
                graphView.status = "mail"
               // graphView.share()
                Sharemail()
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

    fun Sharemail(){
        val imageFile = File(screen())
        val URI = FileProvider.getUriForFile(applicationContext,"com.trendoceans.fileshare.MainActivity.provider",imageFile)
        envoyer(URI)
    }
    fun screen() : String {
        var view = findViewById<View>(R.id.view)
        val bitmap = Bitmap.createBitmap(view.width,view.height,Bitmap.Config.ARGB_8888)
        var canvas= Canvas(bitmap)
        view.draw(canvas)
        val dirPath = getExternalFilesDir(null).toString()!!+ File.separator + "Screenshot"
        val dir = File(dirPath)
        val imagefile = File(dirPath, "test.png")
        imagefile.createNewFile()
        lateinit var outputStream : OutputStream
        try {
            outputStream = FileOutputStream(imagefile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
            outputStream.flush()
        } catch (e: FileNotFoundException) {
            // manage exception
        } catch (e: IOException) {
            // manage exception
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close()
                }
            } catch (exc: Exception) {
            }
        }
        return imagefile.absolutePath
    }
    fun envoyer(Uri : Uri) {
        val mail = Intent(Intent.ACTION_SEND)
        mail.setType("text/plain");
        mail.putExtra(Intent.EXTRA_STREAM, Uri)
        mail.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(Intent.createChooser(mail, "Choisir le logiciel"))
    }

    fun save (){
        RoomService.appDatabase.getConnexionDao().deleteAllConnexionDatas()
        RoomService.appDatabase.getObjetDao().deleteAllObjects()
        try {
            for (connexion in graphView.graphe.myConnexions.values) {
                var objet1 = ObjetData(
                    connexion.objet1.etiquette,
                    connexion.objet1.couleur.color,
                    connexion.objet1.px,
                    connexion.objet1.py
                )
                var objet2 = ObjetData(
                    connexion.objet2.etiquette,
                    connexion.objet2.couleur.color,
                    connexion.objet2.px,
                    connexion.objet2.py
                )

                var idObjet1 = RoomService.appDatabase.getObjetDao().addObjet(objet1)
                var idObjet2 = RoomService.appDatabase.getObjetDao().addObjet(objet2)

                //addConnnexion
                var connexionData = ConnexionData(
                    connexion.name,
                    connexion.color.color,
                    connexion.epaisseur,
                    connexion.px_nom,
                    connexion.py_nom,
                    idObjet1,
                    idObjet2
                    )

                RoomService.appDatabase.getConnexionDao().addConnexionData(connexionData)
                println("C BON !")
            }
            println("c bon 2")

            //println(connexions)
        }
        catch (e: Exception){
            println(e.message)
        }
    }
    fun load(){
        try {
            var connexions = RoomService.appDatabase.getConnexionDao().getAllConnexionDatas()
            var objets = RoomService.appDatabase.getObjetDao().getAllObjects()
            var listobjet =  hashMapOf<String, Objet>()
            var listconnexion = hashMapOf<String, Connexion>()
            var paint = Paint()
            paint.style = Paint.Style.FILL_AND_STROKE

            for (connexion in connexions){
                var con : Connexion
                var obj1 : Objet
                var obj2 : Objet
                var tmpObj : ObjetData
                tmpObj = objets.first({ it.id == Integer.parseInt(connexion.idObjet1.toString()) })
                paint.color= tmpObj.couleur

                obj1 = Objet(
                    tmpObj.etiquette,
                    paint,
                    "",
                    tmpObj.px,
                    tmpObj.py
                )
                tmpObj = objets.first({ it.id == Integer.parseInt(connexion.idObjet2.toString()) })
                paint.color= tmpObj.couleur
                obj2 = Objet(
                    tmpObj.etiquette,
                    paint,
                    "",
                    tmpObj.px,
                    tmpObj.py
                )
                paint.color = connexion.color
                con = Connexion(
                    connexion.name,
                    paint,
                    connexion.epaisseur,
                    obj1,
                    obj2,
                    connexion.px_nom,
                    connexion.py_nom
                )
                listobjet.put(obj1.etiquette,obj1)
                listobjet.put(obj2.etiquette,obj2)
                listconnexion.put(con.name,con)
            }

            var recup = Graph()
            recup.myObjects = HashMap<String, Objet>(listobjet)
            recup.myConnexions = HashMap<String, Connexion>(listconnexion)
            graphView.graphe= recup
            graphView.test()

        } catch (e: Exception){
            println(e.message)
        }

    }
}
