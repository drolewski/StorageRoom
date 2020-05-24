package io.drolewski.storageroom.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import io.drolewski.storageroom.entity.Box
import kotlinx.android.synthetic.main.activity_add_box.*
import kotlinx.android.synthetic.main.activity_add_box.view.*

class AddBox : Activity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_box)

    }

    fun validateField(itemEle: EditText) : Boolean{
        val nameInput = itemEle.text.toString().trim()

        if(nameInput.isEmpty()){
            itemEle.setError("Field can\'t be empty")
            return false
        }
        return true
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
}
