package io.drolewski.storageroom.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_add_box.*
import kotlinx.android.synthetic.main.activity_add_box.takePhoto
import kotlinx.android.synthetic.main.activity_add_single_item.*

class AddBox : Activity(), AdapterView.OnItemSelectedListener {

    var imageBitmap: Bitmap? = null
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_SCAN = 2
    var scannerResult: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_box)

        addBoxButton.setOnClickListener {
            if (validateField(boxName) && validateField(commentaryBox) && validateField(qrCode) && boxPhoto != null) {

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
            itemName.text.clear()
        }

        commentaryBox.setOnClickListener{
            ean.text.clear()
        }

        boxName.setOnClickListener{
            commentary.text.clear()
        }
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

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
