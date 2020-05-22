package io.drolewski.storageroom.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.drolewski.storageroom.database.AppDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AppDatabase(applicationContext)
        Thread{
            val isConfigured = db.localizationDAO().getAll().isEmpty()

            if(isConfigured) {
                val activityToIntent = Intent(
                    applicationContext,
                    MenuStart::class.java
                )
                startActivity(activityToIntent)
            }else{
                configurationButton.setOnClickListener{
                    val activityToIntent = Intent(
                        applicationContext,
                        AddLocalization::class.java
                    )
                    startActivity(activityToIntent)
                }
            }
        }.start()
    }
}