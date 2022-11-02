package fr.istic.mob.networkBT

import androidx.appcompat.app.AppCompatActivity

class test : AppCompatActivity() {
    /*
    var openDialog: Button? = null
    var infoTv: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openDialog = findViewById(R.id.open_dialog)
        infoTv = findViewById(R.id.info_tv)
        openDialog.setOnClickListener(View.OnClickListener { showCustomDialog() })
    }

    //Function to display the custom dialog.
    fun showCustomDialog() {
        val dialog = Dialog(this@MainActivity)
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true)
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.dialog_object)

        //Initializing the views of the dialog.
        val name_obj = dialog.findViewById<EditText>(R.id.obj_name)
        //Radio btn + image a ajouter
        val submitButton = dialog.findViewById<Button>(R.id.Btn_validate)
        val cancelButton = dialog.findViewById<Button>(R.id.Btn_cancel)
        submitButton.setOnClickListener {
            val name = name_obj.text.toString()
            //Radio btn + image a ajouter
            populateInfoTv(name, age, hasAccepted)
            dialog.dismiss()
        }
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun populateInfoTv(name: String?, age: String?, hasAcceptedTerms: Boolean?) {
        infoTv!!.visibility = View.VISIBLE
        var acceptedText = "have"
        if (!hasAcceptedTerms!!) {
            acceptedText = "have not"
        }
        infoTv!!.text = String.format(getString(R.string.info), name, age, acceptedText)
    }*/
}