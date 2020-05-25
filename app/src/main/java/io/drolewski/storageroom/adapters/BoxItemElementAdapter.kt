package io.drolewski.storageroom.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import io.drolewski.storageroom.activities.R
import io.drolewski.storageroom.model.ItemInBoxViewModel

class BoxItemElementAdapter(context: Context?, users: List<ItemInBoxViewModel?>?) :
    ArrayAdapter<ItemInBoxViewModel>(context!!, 0, users!!) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val boxViewModel: ItemInBoxViewModel? = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                .inflate(R.layout.activity_item_in_box_element, parent, false)
        }

        val name = convertView!!.findViewById(R.id.eanInBoxName) as TextView
        val ean = convertView!!.findViewById<View>(R.id.itemInBoxEan) as TextView

        name.text = boxViewModel?.name
        ean.text = boxViewModel?.ean
        return convertView
    }
}