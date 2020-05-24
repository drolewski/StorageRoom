package io.drolewski.storageroom.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import io.drolewski.storageroom.database.AppDatabase
import kotlinx.android.synthetic.main.activity_add_item.*

class AddItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        Thread {
            val db = AppDatabase(this.applicationContext)
            getItems(db)
        }.start()

        addItem.setOnClickListener {
            val activityToIntent = Intent(
                applicationContext,
                AddSingleItem::class.java
            )
            startActivity(activityToIntent)
        }
    }

    fun getItems(db: AppDatabase){
        val objectList = db.objectDAO().getAll()
        val listOfItems = ArrayList<String>()
        for(ele in objectList){
            listOfItems.add(ele.objectName)
        }
        val list = findViewById<ListView>(R.id.itemList)
        val adapter = ArrayAdapter<String>(this, R.layout.list_element, listOfItems)
        list.setAdapter(adapter)
    }
}
