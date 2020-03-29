package io.drolewski.storageroom.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getLocalization(view: View) {
        val intent = Intent(this, LocalizationActivity::class.java)
        startActivity(intent)
    }
}