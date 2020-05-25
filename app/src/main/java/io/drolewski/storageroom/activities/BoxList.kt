package io.drolewski.storageroom.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import io.drolewski.storageroom.adapters.BoxElementAdapter
import io.drolewski.storageroom.adapters.ItemElementAdapter
import io.drolewski.storageroom.database.AppDatabase
import io.drolewski.storageroom.model.BoxViewModel
import io.drolewski.storageroom.model.ObjectViewModel
import kotlinx.android.synthetic.main.activity_box_list.*
import kotlinx.android.synthetic.main.activity_edit_box.*
import java.io.ByteArrayInputStream

class BoxList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_box_list)

        Thread {
            val db = AppDatabase(this.applicationContext)
            val itemsList = db.boxDAO().getAllWithPhotos()
            val adapter = this.applicationContext.let {
                itemsList.map {
                        value ->

                    BoxViewModel(
                        value.box.boxName,
                        value.box.localization,
                        value.box.qrCode,
                        byteToBit(value.photo[0].image)
                    )
                }.let { it1 ->
                    BoxElementAdapter(it, it1)
                }
            }
            this.runOnUiThread(java.lang.Runnable {
                val list: ListView? = findViewById(R.id.boxList)
                list!!.adapter = adapter
            })
        }.start()

        boxList.setOnItemClickListener{parent, view, position, id ->
            val activityToIntent = Intent(
                applicationContext,
                EditBox::class.java
            )
            activityToIntent.putExtra("id", id)
            startActivity(activityToIntent)
        }

        addBoxButton.setOnClickListener {
            val activityToIntent = Intent(
                applicationContext,
                AddBox::class.java
            )
            startActivity(activityToIntent)
        }
    }

    fun byteToBit(byteArray: ByteArray?): Bitmap?{
        if(byteArray != null) {
            val arrayIn = ByteArrayInputStream(byteArray)
            val bitmap = BitmapFactory.decodeStream(arrayIn)
            return bitmap
        }
        return null
    }
}
