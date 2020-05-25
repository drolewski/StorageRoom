package io.drolewski.storageroom.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import io.drolewski.storageroom.adapters.BoxElementAdapter
import io.drolewski.storageroom.adapters.BoxItemElementAdapter
import io.drolewski.storageroom.adapters.OnSpinerSelectedListner
import io.drolewski.storageroom.database.AppDatabase
import io.drolewski.storageroom.entity.Object
import io.drolewski.storageroom.model.BoxViewModel
import io.drolewski.storageroom.model.ItemInBoxViewModel
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.activity_box_list.*
import kotlinx.android.synthetic.main.activity_edit_box.*
import kotlinx.android.synthetic.main.activity_item_in_box_element.*
import kotlinx.android.synthetic.main.activity_item_in_box_element.view.*

class EditBox : AppCompatActivity() {


    var selectedLocalizationInSpinner = OnSpinerSelectedListner()
    var selectedItemSpinner = OnSpinerSelectedListner()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_box)

        val spinner = findViewById<Spinner>(R.id.editBoxLocalization)
        spinner.setOnItemSelectedListener(selectedLocalizationInSpinner)


        val itemSpinner = findViewById<Spinner>(R.id.addItemSpinner)
        itemSpinner.setOnItemSelectedListener(selectedItemSpinner)

        addLocalizationToDropdown(spinner)
        addItemToDropDown(itemSpinner)

        val intent = intent
        val boxId = intent.extras!!.get("id").toString().toInt()

        Thread{
            val db = AppDatabase(applicationContext)
            var box = db.boxDAO().getAll()[boxId]
            editBoxName.setText(box.boxName, TextView.BufferType.EDITABLE)
            editBoxCommentary.setText(box.boxCommentary, TextView.BufferType.EDITABLE)
            editBoxQrCode.text = box.qrCode
            val localizationList = db.localizationDAO().getAll()
            var index = 0
            var ogolneIndex = 0;
            for(i in localizationList){
                if(i.localizationName == box.localization){
                    break;
                }
                if(i.localizationName == "ogolne"){
                    ogolneIndex = index
                }
                index++
            }
            if(index >= localizationList.size){
                index = ogolneIndex
            }
            spinner.setSelection(index)

            val itemsList = db.objectDAO().getAllWithBoxId(box.boxId)
            val adapter = this.applicationContext.let {
                itemsList.map {
                        value ->
                    ItemInBoxViewModel(
                        value.objectName,
                        value.ean
                    )
                }.let { it1 ->
                    BoxItemElementAdapter(it, it1)
                }
            }
            val list: ListView? = findViewById(R.id.editBoxItemList)
            list!!.adapter = adapter
        }.start()

        addItemButton.setOnClickListener {
            Thread{
                val db = AppDatabase(applicationContext)
                var box = db.boxDAO().getAll()[boxId]
                val item = db.objectDAO().getItemWithName(selectedItemSpinner.selectedItemInSpinner!!)[0]
                item.boxId = box.boxId
                db.objectDAO().update(item)
            }.start()
            val activityToIntent = Intent(
                applicationContext,
                MenuStart::class.java
            )
            startActivity(activityToIntent)
        }

        editBoxDelete.setOnClickListener {
            Thread {
                val db = AppDatabase(applicationContext)
                var box = db.boxDAO().getAll()[boxId]
                db.boxDAO().delete(box)
            }.start()
            val activityToIntent = Intent(
                applicationContext,
                MenuStart::class.java
            )
            startActivity(activityToIntent)
        }

        editBoxUpdate.setOnClickListener {
            Thread{
                val db = AppDatabase(applicationContext)
                var box = db.boxDAO().getAll()[boxId]


                box.boxName = editBoxName.text.toString()
                box.boxCommentary = editBoxCommentary.text.toString()
                box.localization = selectedLocalizationInSpinner.selectedItemInSpinner.toString()
                db.boxDAO().update(box)
            }.start()
            val activityToIntent = Intent(
                applicationContext,
                MenuStart::class.java
            )
            startActivity(activityToIntent)
        }

        editBoxItemList.setOnItemClickListener{parent, view, position, id ->
            Thread {
                val db = AppDatabase(applicationContext)
                val bId = db.boxDAO().getAll()[boxId].boxId
                val item = db.objectDAO().getAllWithBoxId(bId)[id.toInt()]
                item.boxId = null
                db.objectDAO().update(item)
            }.start()
            val activityToIntent = Intent(
                applicationContext,
                BoxList::class.java
            )
            startActivity(activityToIntent)
        }
    }

    private fun addItemToDropDown(spinner: Spinner) {
        Thread{
            val db = AppDatabase(applicationContext)
            val itemList = db.objectDAO().getAll()
            val list = ArrayList<String>()
            for( i in itemList){
                if(i.boxId == null){
                    list.add(i.objectName)
                }
            }
            val adapter = ArrayAdapter<String>(this, R.layout.list_element, list)
            adapter.setDropDownViewResource(R.layout.list_element)
            spinner.setAdapter(adapter)
        }.start()
    }

    private fun addLocalizationToDropdown(spinner: Spinner) {
        Thread{
            val db = AppDatabase(applicationContext)
            val localizationList = db.localizationDAO().getAll()
            val list = ArrayList<String>()
            for( i in localizationList){
                list.add(i.localizationName)
            }
            val adapter = ArrayAdapter<String>(this, R.layout.list_element, list)
            adapter.setDropDownViewResource(R.layout.list_element)
            spinner.setAdapter(adapter)
        }.start()
    }
}
