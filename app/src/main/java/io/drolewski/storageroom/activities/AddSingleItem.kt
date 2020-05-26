package io.drolewski.storageroom.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.integration.android.IntentIntegrator
import io.drolewski.storageroom.adapters.ItemCategoryElementAdapter
import io.drolewski.storageroom.adapters.OnSpinerSelectedListner
import io.drolewski.storageroom.database.AppDatabase
import io.drolewski.storageroom.entity.Object
import io.drolewski.storageroom.entity.ObjectCategoryCrossRef
import io.drolewski.storageroom.entity.Photo
import io.drolewski.storageroom.model.CategoryViewModel
import kotlinx.android.synthetic.main.activity_add_single_item.*
import java.io.ByteArrayOutputStream

class AddSingleItem : AppCompatActivity() {

    var imageBitmap: Bitmap? = null
    val REQUEST_IMAGE_CAPTURE = 1
    val PERMISSION_REQUEST_CODE = 200
    var scannerResult: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_item)

        if(!checkPermission()){
            requestPermission()
        }

        addIt.setOnClickListener {
            var preparedEAN: String? = null
            if (validateField(itemName) && validateField(commentary)) {
                if (ean.text.toString().trim().isNotEmpty()) {
                    preparedEAN = ean.text.toString().trim()
                }

                if(imageBitmap == null){
                    takePhoto.setError("Please take photo")
                }else{
                    Thread {
                        val db = AppDatabase(applicationContext)
                        val objId = db.objectDAO().getAll().size + 1
                        db.objectDAO().add(
                            Object(
                                objId,
                                itemName.text.toString().trim(),
                                preparedEAN,
                                commentary.text.toString().trim(),
                                null
                            )
                        )
                        val stream = ByteArrayOutputStream()
                        imageBitmap!!.compress(Bitmap.CompressFormat.PNG, 90, stream)
                        db.photoDAO()
                            .add(Photo(db.photoDAO().getAll().size + 1, stream.toByteArray(), objId))
                    }.start()
                    val activityToIntent = Intent(
                        applicationContext,
                        AddItem::class.java
                    )
                    startActivity(activityToIntent)
                }
            }
        }

        takePhoto.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }

        itemName.setOnClickListener {
            itemName.text.clear()
        }

        ean.setOnClickListener {
            ean.text.clear()
        }

        commentary.setOnClickListener {
            commentary.text.clear()
        }

        takeEANCodeFromItem.setOnClickListener {
            IntentIntegrator(this).initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageBitmap = data!!.extras!!.get("data") as Bitmap
            itemImage.setImageBitmap(imageBitmap)
        }else if(resultCode == RESULT_OK){
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            scannerResult = result.contents.toString()
            val editText = findViewById<EditText>(R.id.ean)
            editText.setText(scannerResult, TextView.BufferType.EDITABLE)
        }
    }

    fun checkPermission(): Boolean{
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            return false
        }
        return true
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE);
    }

    fun validateField(itemEle: EditText): Boolean {
        val nameInput = itemEle.text.toString().trim()

        if (nameInput.isEmpty()) {
            itemEle.setError("Field can\'t be empty")
            return false
        }
        return true
    }
}
