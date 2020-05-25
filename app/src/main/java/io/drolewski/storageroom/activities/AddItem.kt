package io.drolewski.storageroom.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import io.drolewski.storageroom.adapters.ItemElementAdapter
import io.drolewski.storageroom.database.AppDatabase
import io.drolewski.storageroom.entity.ObjectWithPhotos
import io.drolewski.storageroom.model.ObjectViewModel
import kotlinx.android.synthetic.main.activity_add_item.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class AddItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        Thread {
            val db = AppDatabase(this.applicationContext)
            val itemsList = db.objectDAO().getAllWithPhotos()
            val adapter = this.applicationContext.let {
                itemsList.map {
                    value ->

                        ObjectViewModel(
                            value.objectThing.objectName,
                            value.objectThing.ean.toString(),
                            value.objectThing.commentary,
                            byteToBit(value.photos[0].image)
                        )
                }.let { it1 ->
                    ItemElementAdapter(it, it1)
                }
            }

            val list: ListView? = findViewById(R.id.itemList)
            list!!.adapter = adapter
        }.start()

        addItem.setOnClickListener {
            val activityToIntent = Intent(
                applicationContext,
                AddSingleItem::class.java
            )
            startActivity(activityToIntent)
        }

        itemList.setOnItemClickListener { parent, view, position, id ->
            val activityToIntent = Intent(
                applicationContext,
                EditObject::class.java
            )
            activityToIntent.putExtra("id", id)
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
