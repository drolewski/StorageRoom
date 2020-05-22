package io.drolewski.storageroom.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.findNavController
import io.drolewski.storageroom.database.AppDatabase
import io.drolewski.storageroom.entity.Localization
import kotlinx.android.synthetic.main.activity_localization_list.*
import kotlinx.android.synthetic.main.first_fragment.*

class LocalizationList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localization_list)

        Thread {
            val db = AppDatabase(this.applicationContext)
            getFromDb(db)
        }.start()

        val error = AlertDialog.Builder(applicationContext)
            .setMessage("Invalid data in form, please try again.")
            .setPositiveButton("OK", null)

        addLoc.setOnClickListener {
            try {
                if (locInput.text.toString() == "") throw Exception()
                Thread {
                    val dbA = applicationContext?.let { it1 -> AppDatabase(it1) }
                    if (dbA != null) {
                        dbA.localizationDAO().add(Localization(locInput.text.toString()))
                    } else {
                        throw Exception()
                    }
                }.start()
                val activityToIntent = Intent(
                    applicationContext,
                    MenuStart::class.java
                )
                startActivity(activityToIntent)
            } catch (exc: Exception) {
                error.show()
            }
        }
    }

    fun getFromDb(db: AppDatabase){
        val localizationList = db.localizationDAO().getAll()
        val listOfLocalization = ArrayList<String>()
        for(ele in localizationList){
            listOfLocalization.add(ele.localizationName)
        }
        val list = findViewById<ListView>(R.id.listLoc)
        val adapter = ArrayAdapter<String>(this, R.layout.list_element, listOfLocalization)
        list.setAdapter(adapter)
    }
}
