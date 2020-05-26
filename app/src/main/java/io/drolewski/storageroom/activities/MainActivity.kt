package io.drolewski.storageroom.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.integration.android.IntentIntegrator
import io.drolewski.storageroom.database.AppDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!checkPermission()){
            requestPermission()
        }

        val db = AppDatabase(applicationContext)
        Thread{
            val isConfigured = db.localizationDAO().getAll().size

            if(isConfigured > 0) {
                configurationButton.setOnClickListener {
                    val activityToIntent = Intent(
                        applicationContext,
                        MenuStart::class.java
                    )
                    startActivity(activityToIntent)
                }
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

    fun checkPermission(): Boolean{
        if(ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
            ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            return false
        }
        return true
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE),
            IntentIntegrator.REQUEST_CODE
        );
    }
}