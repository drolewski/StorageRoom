package io.drolewski.storageroom.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import io.drolewski.storageroom.adapters.BoxItemElementAdapter
import io.drolewski.storageroom.adapters.ItemCategoryElementAdapter
import io.drolewski.storageroom.adapters.OnSpinerSelectedListner
import io.drolewski.storageroom.database.AppDatabase
import io.drolewski.storageroom.entity.Category
import io.drolewski.storageroom.entity.ObjectCategoryCrossRef
import io.drolewski.storageroom.model.CategoryViewModel
import io.drolewski.storageroom.model.ItemInBoxViewModel
import kotlinx.android.synthetic.main.activity_edit_object.*

class EditObject : AppCompatActivity() {

    var selectedCategory = OnSpinerSelectedListner()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_object)

        val spinner = findViewById<Spinner>(R.id.addCategorySpinner)
        spinner.setOnItemSelectedListener(selectedCategory)

        val intent = intent
        val itemId = intent.extras!!.get("id").toString().toInt()

        Thread{
            val db = AppDatabase(applicationContext)
            editObjectEAN.text = db.objectDAO().getAllWithPhotos()[itemId].objectThing.ean

            val objectId = db.objectDAO().getAllWithPhotos()[itemId].objectThing.objectId
            val itemsList = db.categoryObjectDAO().getByObjectId(objectId)
            var categoryList: ArrayList<Category>? = ArrayList<Category>()
            for(i in itemsList){
                categoryList!!.add(db.categoryDAO().getById(i.categoryId))
            }
            val adapter = this.applicationContext.let {
                categoryList!!.map {
                        value ->
                    CategoryViewModel(
                        value.categoryName

                    )
                }.let { it1 ->
                    ItemCategoryElementAdapter(it, it1)
                }
            }
            val list: ListView? = findViewById(R.id.editObjectCategoryList)
            list!!.adapter = adapter
        }.start()

        addCategoryToDropDown(spinner)

        editObjectName.setOnClickListener {
            editObjectName.text.clear()
        }

        editObjectUpdate.setOnClickListener {
            Thread{
                val db = AppDatabase(applicationContext)
                val itemFromDb = db.objectDAO().getAllWithPhotos()[itemId].objectThing
                itemFromDb.commentary = editObjectCommentary.text.toString()
                itemFromDb.objectName = editObjectName.text.toString()
                db.objectDAO().update(itemFromDb)
            }.start()
            val activityToIntent = Intent(
                applicationContext,
                AddItem::class.java
            )
            startActivity(activityToIntent)
        }

        editObjectDelete.setOnClickListener {
            Thread{
                val db = AppDatabase(applicationContext)
                val itemFromDb = db.objectDAO().getAllWithPhotos()[itemId].objectThing
                db.objectDAO().delete(itemFromDb)
            }.start()
            val activityToIntent = Intent(
                applicationContext,
                AddItem::class.java
            )
            startActivity(activityToIntent)
        }

        addCategoryButton.setOnClickListener {
            Thread{
                val db = AppDatabase(applicationContext)
                val itemFromDb = db.objectDAO().getAllWithPhotos()[itemId].objectThing
                val categories = db.categoryDAO().getAll()
                var categoryFromDb: Category? = null
                for(i in categories){
                    if(i.categoryName == selectedCategory.selectedItemInSpinner.toString()){
                        categoryFromDb = i
                    }
                }
                var flag = false
                val categoriesInItem = db.categoryObjectDAO().getByObjectId(itemFromDb.objectId)
                for(i in categoriesInItem){
                    if(i.categoryId == categoryFromDb!!.categoryId){
                        flag = true
                    }
                }
                if(!flag){
                    db.categoryObjectDAO().add(ObjectCategoryCrossRef(itemFromDb.objectId, categoryFromDb!!.categoryId))
                }
            }.start()
            val activityToIntent = Intent(
                applicationContext,
                EditObject::class.java
            )
            activityToIntent.putExtra("id", itemId)
            startActivity(activityToIntent)
        }

        editObjectCategoryList.setOnItemClickListener { parent, view, position, id ->
            Thread {
                val db = AppDatabase(applicationContext)
                val oId = db.objectDAO().getAllWithPhotos()[itemId].objectThing.objectId
                val categoriesWithObjects = db.categoryObjectDAO().getByObjectId(oId)
                val selectedCategoryId = categoriesWithObjects[position].categoryId

                var categoryToDelete = ObjectCategoryCrossRef(-1,-1)
                for(i in categoriesWithObjects){
                    if(i.objectId == oId && i.categoryId == selectedCategoryId){
                        categoryToDelete = i
                        break
                    }
                }
                if(categoryToDelete.categoryId > -1 && categoryToDelete.objectId > -1){
                    db.categoryObjectDAO().delete(categoryToDelete)
                }
            }.start()
            val activityToIntent = Intent(
                applicationContext,
                EditObject::class.java
            )
            activityToIntent.putExtra("id", itemId)
            startActivity(activityToIntent)
        }
    }

    private fun addCategoryToDropDown(spinner: Spinner) {
        Thread{
            val db = AppDatabase(applicationContext)
            val categoryList = db.categoryDAO().getAll()
            val list = ArrayList<String>()
            for( i in categoryList){
                list.add(i.categoryName)
            }
            val adapter = ArrayAdapter<String>(this, R.layout.list_element, list)
            adapter.setDropDownViewResource(R.layout.list_element)
            spinner.setAdapter(adapter)
        }.start()
    }
}
