package io.drolewski.storageroom.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import io.drolewski.storageroom.database.AppDatabase
import io.drolewski.storageroom.entity.Category
import io.drolewski.storageroom.entity.Localization
import kotlinx.android.synthetic.main.activity_add_category.*
import kotlinx.android.synthetic.main.activity_localization_list.*

class AddCategory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        Thread {
            val db = AppDatabase(this.applicationContext)
            getFromDb(db)
        }.start()

        val error = AlertDialog.Builder(applicationContext)
            .setMessage("Invalid data in form, please try again.")
            .setPositiveButton("OK", null)

        addCategoryInList.setOnClickListener {
            try {
                if (categoryInput.text.toString() == "") throw Exception()
                Thread {
                    val dbA = applicationContext?.let { it1 -> AppDatabase(it1) }
                    if (dbA != null) {
                        dbA.categoryDAO().add(Category(dbA.categoryDAO().getAll().size + 1, categoryInput.text.toString()))
                    } else {
                        throw Exception()
                    }
                }.start()
                val activityToIntent = Intent(
                    applicationContext,
                    AddCategory::class.java
                )
                startActivity(activityToIntent)
            } catch (exc: Exception) {
                error.show()
            }
        }

        categoryInput.setOnClickListener{
            categoryInput.text.clear()
        }

        categoryList.setOnItemClickListener{parent, view, position, id ->
            Thread {
                val db = AppDatabase(applicationContext)
                val cat = db.categoryDAO().getAll()[position]
                db.categoryDAO().delete(cat)
            }.start()
            val activityToIntent = Intent(
                applicationContext,
                AddCategory::class.java
            )
            startActivity(activityToIntent)

        }
    }

    fun getFromDb(db: AppDatabase){
        val localizationList = db.categoryDAO().getAll()
        val listOfLocalization = ArrayList<String>()
        for(ele in localizationList){
            listOfLocalization.add(ele.categoryName)
        }
        val list = findViewById<ListView>(R.id.categoryList)
        val adapter = ArrayAdapter<String>(this, R.layout.list_element, listOfLocalization)
        list.setAdapter(adapter)
    }
}
