package io.drolewski.storageroom.adapters

import android.view.View
import android.widget.AdapterView

public class OnSpinerSelectedListner : AdapterView.OnItemSelectedListener {

    var selectedItemInSpinner: String? = null
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedItemInSpinner = parent!!.getItemAtPosition(position).toString()
    }
}