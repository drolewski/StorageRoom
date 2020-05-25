package io.drolewski.storageroom.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.drolewski.storageroom.database.AppDatabase
import io.drolewski.storageroom.entity.Object
import io.drolewski.storageroom.entity.Photo
import kotlinx.android.synthetic.main.activity_add_single_item.*
import java.io.ByteArrayOutputStream

class AddSingleItem : AppCompatActivity() {

    var imageBitmap: Bitmap? = null
    val REQUEST_IMAGE_CAPTURE = 1
    val PERMISSION_REQUEST_CODE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_item)

        if(!checkPermission()){
            requestPermission()
        }

        addIt.setOnClickListener {
            var preparedEAN: String? = null
            if (validateField(itemName) && validateField(commentary) && imageBitmap != null) {
                if (ean.text.toString().trim().isNotEmpty()) {
                    preparedEAN = ean.text.toString().trim()
                }

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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageBitmap = data!!.extras!!.get("data") as Bitmap
            itemImage.setImageBitmap(imageBitmap)
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
