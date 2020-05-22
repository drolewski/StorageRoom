package io.drolewski.storageroom.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_localization_activity.*

class AddLocalization : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_localization_activity)
        setSupportActionBar(toolbar)
        title = "Setup the application"
    }
}