package io.drolewski.storageroom.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu_start.*

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
    }
}
