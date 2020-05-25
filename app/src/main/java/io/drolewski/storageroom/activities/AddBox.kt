package io.drolewski.storageroom.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import io.drolewski.storageroom.adapters.OnSpinerSelectedListner
import io.drolewski.storageroom.database.AppDatabase
import io.drolewski.storageroom.entity.Box
import io.drolewski.storageroom.entity.Photo
import kotlinx.android.synthetic.main.activity_add_box.*
import kotlinx.android.synthetic.main.activity_add_box.takePhoto
import java.io.ByteArrayOutputStream

class AddBox : Activity() {

    var imageBitmap: Bitmap? = null
    val REQUEST_IMAGE_CAPTURE = 1
    var scannerResult: String? = null
    var selectedItemInSpinner = OnSpinerSelectedListner()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_box)

        addItemsToDropdown()
        val spinner = findViewById<Spinner>(R.id.localizationList)
        spinner.setOnItemSelectedListener(selectedItemInSpinner)

        addBoxButton.setOnClickListener {
            if (validateField(boxName) && validateField(commentaryBox) && validateField(qrCode) && boxPhoto != null) {
                Thread{
                    val db = AppDatabase(applicationContext)
                    val boxId = db.boxDAO().getAll().size + 1
                    val photoId = db.photoDAO().getAll().size + 1
                    val stream = ByteArrayOutputStream()
                    imageBitmap!!.compress(Bitmap.CompressFormat.PNG, 90, stream)
                    db.photoDAO().add(Photo(
                        photoId,
                        stream.toByteArray(),
                        null
                    ))
                    if (selectedItemInSpinner.selectedItemInSpinner == null){
                        selectedItemInSpinner.selectedItemInSpinner = "everywhere"
                    }
                    db.boxDAO().add(Box(
                        boxId,
                        boxName.text.toString().trim(),
                        selectedItemInSpinner.selectedItemInSpinner!!,
                        commentaryBox.text.toString().trim(),
                        qrCode.text.toString().trim(),
                        photoId
                    ))
                }.start()
                val activityToIntent = Intent(
                    applicationContext,
                    MenuStart::class.java
                )
                startActivity(activityToIntent)
            }
        }

        scanQR.setOnClickListener {
            IntentIntegrator(this).initiateScan()
        }

        takePhoto.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }

        qrCode.setOnClickListener{
            qrCode.text.clear()
        }

        commentaryBox.setOnClickListener{
            commentaryBox.text.clear()
        }

        boxName.setOnClickListener{
            boxName.text.clear()
        }
    }

    private fun addItemsToDropdown() {
        val spinner = findViewById<Spinner>(R.id.localizationList)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            imageBitmap = data!!.extras!!.get("data") as Bitmap
            boxPhoto.setImageBitmap(imageBitmap)
        } else if (resultCode == AppCompatActivity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            scannerResult = result.contents.toString()
            val editText = findViewById<EditText>(R.id.qrCode)
            editText.setText(scannerResult, TextView.BufferType.EDITABLE)
        }
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
