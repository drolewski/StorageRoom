package io.drolewski.storageroom.activities

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.zxing.integration.android.IntentIntegrator
import io.drolewski.storageroom.adapters.BoxItemElementAdapter
import io.drolewski.storageroom.adapters.OnSpinerSelectedListner
import io.drolewski.storageroom.database.AppDatabase
import io.drolewski.storageroom.model.ItemInBoxViewModel
import kotlinx.android.synthetic.main.activity_add_box.*
import kotlinx.android.synthetic.main.activity_edit_box.*
import kotlinx.android.synthetic.main.activity_search_box.*

class SearchBox : AppCompatActivity() {

    var selectedLocalizationInSpinner = OnSpinerSelectedListner()
    var selectedItemSpinner = OnSpinerSelectedListner()
    var scannerResult : String? = null
    var scanner = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_box)

        val spinner = findViewById<Spinner>(R.id.findBoxLocalization)
        spinner.setOnItemSelectedListener(selectedLocalizationInSpinner)

        val itemSpinner = findViewById<Spinner>(R.id.findBoxAddItem)
        itemSpinner.setOnItemSelectedListener(selectedItemSpinner)

        addLocalizationToDropdown(spinner)
        addItemToDropDown(itemSpinner)

        val intent = intent
        val boxId = intent.extras!!.get("boxId").toString().toInt()

        Thread{
            val db = AppDatabase(applicationContext)
            val box = db.boxDAO().getById(boxId)
            findBoxName.setText(box.boxName, TextView.BufferType.EDITABLE)
            findBoxCommentary.setText(box.boxCommentary, TextView.BufferType.EDITABLE)
            findBoxQrCode.text = box.qrCode

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

            val itemsList = db.objectDAO().getAllWithBoxId(boxId)
            val adapter = this.applicationContext.let {
                itemsList.map {
                        value ->
                    ItemInBoxViewModel(
                        value.objectName!!,
                        value.ean
                    )
                }.let { it1 ->
                    BoxItemElementAdapter(it, it1)
                }
            }
            this.runOnUiThread(java.lang.Runnable {
                val list: ListView? = findViewById(R.id.findBoxItems)
                list!!.adapter = adapter
            })
        }.start()

        findBoxAddItemButton.setOnClickListener {
            Thread{
                val db = AppDatabase(applicationContext)
                var box = db.boxDAO().getById(boxId)
                val item = db.objectDAO().getItemWithName(selectedItemSpinner.selectedItemInSpinner!!)[0]
                item.boxId = box.boxId
                db.objectDAO().update(item)
            }.start()
            val activityToIntent = Intent(
                applicationContext,
                SearchBox::class.java
            )
            activityToIntent.putExtra("boxId", boxId)
            startActivity(activityToIntent)
        }

        findBoxItems.setOnItemClickListener { parent, view, position, id ->
            Thread {
                val db = AppDatabase(applicationContext)
                val item = db.objectDAO().getAllWithBoxId(boxId)[id.toInt()]
                item.boxId = null
                db.objectDAO().update(item)
            }.start()
            val activityToIntent = Intent(
                applicationContext,
                SearchBox::class.java
            )
            activityToIntent.putExtra("boxId", boxId)
            startActivity(activityToIntent)
        }

        findBoxUpdate.setOnClickListener {
            Thread{
                val db = AppDatabase(applicationContext)
                var box = db.boxDAO().getById(boxId)
                box.boxName = findBoxName.text.toString()
                box.boxCommentary = findBoxCommentary.text.toString()
                box.localization = selectedLocalizationInSpinner.selectedItemInSpinner.toString()
                db.boxDAO().update(box)
            }.start()
            val activityToIntent = Intent(
                applicationContext,
                SearchBox::class.java
            )
            activityToIntent.putExtra("boxId", boxId)
            startActivity(activityToIntent)
        }

        findBoxByQRCode.setOnClickListener {
            scanner = true
            IntentIntegrator(this).initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            scannerResult = result.contents.toString()
            if(scannerResult == findBoxQrCode.text.toString()){
                val positiveSound = MediaPlayer.create(this, R.raw.positive)
                positiveSound.start()
                findBoxByQRCode.visibility = View.INVISIBLE
            }else{
                val negativeSound = MediaPlayer.create(this, R.raw.negative)
                negativeSound.start()
            }
            scanner = false
        }
    }

    private fun addItemToDropDown(spinner: Spinner) {
        Thread{
            val db = AppDatabase(applicationContext)
            val itemList = db.objectDAO().getAll()
            val list = ArrayList<String>()
            for( i in itemList){
                if(i.boxId == null){
                    list.add(i.objectName!!)
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
