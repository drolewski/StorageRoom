package io.drolewski.storageroom.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import io.drolewski.storageroom.database.AppDatabase
import kotlinx.android.synthetic.main.activity_menu_start.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class MenuStart : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_start)

        locList.setOnClickListener{
            val activityToIntent = Intent(
                applicationContext,
                LocalizationList::class.java
            )
            startActivity(activityToIntent)
        }

        addItem.setOnClickListener{
            val activityToIntent = Intent(
                applicationContext,
                AddItem::class.java
            )
            startActivity(activityToIntent)
        }

        addBox.setOnClickListener {
            val activityToIntent = Intent(
                applicationContext,
                BoxList::class.java
            )
            startActivity(activityToIntent)
        }

        addCategory.setOnClickListener {
            val activityToIntent = Intent(
                applicationContext,
                AddCategory::class.java
            )
            startActivity(activityToIntent)
        }

        searchButton.setOnClickListener {
            val activityToIntent = Intent(
                applicationContext,
                Search::class.java
            )
            startActivity(activityToIntent)
        }

        button_export.setOnClickListener {
            //export
            val db = AppDatabase(applicationContext)
            db.exportDatabase(applicationContext)
        }
    }
}
