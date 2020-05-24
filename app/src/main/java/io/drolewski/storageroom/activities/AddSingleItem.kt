package io.drolewski.storageroom.activities

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import io.drolewski.storageroom.database.AppDatabase
import io.drolewski.storageroom.entity.Object
import io.drolewski.storageroom.entity.Photo
import kotlinx.android.synthetic.main.activity_add_single_item.*
import java.io.ByteArrayOutputStream

class AddSingleItem : AppCompatActivity() {

    var imageBitmap: Bitmap? = null
    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_item)


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
                    db.photoDAO().add(Photo(db.photoDAO().getAll().size + 1, stream.toByteArray(), objId))
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageBitmap = data!!.extras!!.get("data") as Bitmap
            itemImage.setImageBitmap(imageBitmap)
        }
    }

    fun validateField(itemEle: EditText) : Boolean{
        val nameInput = itemEle.text.toString().trim()

        if(nameInput.isEmpty()){
            itemEle.setError("Field can\'t be empty")
            return false
        }
        return true
    }
}
