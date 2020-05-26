package io.drolewski.storageroom.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.google.zxing.integration.android.IntentIntegrator
import io.drolewski.storageroom.adapters.BoxItemElementAdapter
import io.drolewski.storageroom.adapters.SearchItemAdapter
import io.drolewski.storageroom.database.AppDatabase
import io.drolewski.storageroom.entity.Box
import io.drolewski.storageroom.entity.Object
import io.drolewski.storageroom.entity.ObjectWithCategory
import io.drolewski.storageroom.entity.ObjectWithPhotos
import io.drolewski.storageroom.model.ItemInBoxViewModel
import io.drolewski.storageroom.model.SearchItemViewModel
import kotlinx.android.synthetic.main.activity_add_single_item.*
import kotlinx.android.synthetic.main.activity_search.*
import java.io.ByteArrayInputStream

class Search : AppCompatActivity() {

    var imageBitmap: Bitmap? = null
    val REQUEST_IMAGE_CAPTURE = 1
    var isEAN = false
    var listOfSearch = ArrayList<ObjectWithPhotos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        startSearching.setOnClickListener {
            Thread {
                val db = AppDatabase(applicationContext)
                var searching = searchName.text.toString()
                if(searching.trim() == ""){
                    searchName.setError("Complete this field")
                }else{
                    searching = searching.toUpperCase()
                    searching = "$searching%"
                    val objectsFromDb = db.objectDAO().getSearching(searching)
                    val categoriesFromDb = db.categoryDAO().getSearching(searching)
                    var objectAndGivenCategory = ArrayList<ObjectWithPhotos>()
                    for(i in categoriesFromDb){
                        val cat = db.categoryObjectDAO().getByCategoryID(i.categoryId)
                        if(cat.size > 0){
                            val objectCategoryId = cat[0]
                            objectAndGivenCategory.add(db.objectDAO().getByIdWithPhotos(objectCategoryId.objectId)[0])
                        }
                    }
                    for(i in objectsFromDb){
                        objectAndGivenCategory.add(i)
                    }
                    listOfSearch = objectAndGivenCategory
                    val adapter = this.applicationContext.let {
                        objectAndGivenCategory.map {
                                value ->
                            SearchItemViewModel(
                                value.objectThing.objectName,
                                byteToBit(value.photos[0].image)
                            )
                        }.let { it1 ->
                            SearchItemAdapter(it, it1)
                        }
                    }
                    this.runOnUiThread(java.lang.Runnable {
                        val list: ListView? = findViewById(R.id.searchingResult)
                        list!!.adapter = adapter
                    })
                }
            }.start()
        }

        searchingResult.setOnItemClickListener { parent, view, position, id ->
            val activityToIntent = Intent(
                applicationContext,
                SearchBox::class.java
            )
            if(listOfSearch[position].objectThing.boxId == null){
                startSearching.setError("There are items without box")
            }else{
                activityToIntent.putExtra("boxId", listOfSearch[position].objectThing.boxId)
                startActivity(activityToIntent)
            }

        }
        searchName.setOnClickListener {
            searchName.text.clear()
        }

        searchByEan.setOnClickListener {
            isEAN = true
            IntentIntegrator(this).initiateScan()
        }

        searchByQRCode.setOnClickListener {
            isEAN = false
            IntentIntegrator(this).initiateScan()
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (isEAN && resultCode == RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            val scannerResult = result.contents.toString()
            Thread{
                val db = AppDatabase(applicationContext)
                val searchedObjects = db.objectDAO().getItemByEan(scannerResult)
                listOfSearch = searchedObjects as ArrayList<ObjectWithPhotos>
                val adapter = this.applicationContext.let {
                    searchedObjects.map {
                            value ->
                        SearchItemViewModel(
                            value.objectThing.objectName,
                            byteToBit(value.photos[0].image)
                        )
                    }.let { it1 ->
                        SearchItemAdapter(it, it1)
                    }
                }
                this.runOnUiThread(java.lang.Runnable {
                    val list: ListView? = findViewById(R.id.searchingResult)
                    list!!.adapter = adapter
                })
            }.start()
        }else if(resultCode == RESULT_OK && !isEAN){
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            val scannerResult = result.contents.toString()
            Thread{
                val db = AppDatabase(applicationContext)
                val queryRes = db.boxDAO().getAll()
                val resBox = ArrayList<Box>()
                for(i in queryRes){
                    if(i.qrCode == scannerResult){
                        resBox.add(i)
                    }
                }
                if(resBox.isNotEmpty()) {
                    val boxWithQrCode = resBox[0]
                    val itemInBox = db.objectDAO().getAllWithPhotos()
                    val res = ArrayList<ObjectWithPhotos>()

                    for(i in itemInBox){
                        if(i.objectThing.boxId == boxWithQrCode.boxId){
                            res.add(i)
                        }
                    }
                    listOfSearch = res
                    val adapter = this.applicationContext.let {
                        res.map { value ->
                            SearchItemViewModel(
                                value.objectThing.objectName,
                                byteToBit(value.photos[0].image)
                            )
                        }.let { it1 ->
                            SearchItemAdapter(it, it1)
                        }
                    }
                    this.runOnUiThread(java.lang.Runnable {
                        val list: ListView? = findViewById(R.id.searchingResult)
                        list!!.adapter = adapter
                    })
                }
            }.start()
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
