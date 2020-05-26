package io.drolewski.storageroom.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import io.drolewski.storageroom.activities.R
import io.drolewski.storageroom.model.CategoryViewModel
import io.drolewski.storageroom.model.ItemInBoxViewModel

class ItemCategoryElementAdapter (context: Context?, users: List<CategoryViewModel?>?) :
    ArrayAdapter<CategoryViewModel>(context!!, 0, users!!) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val boxViewModel: CategoryViewModel? = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                .inflate(R.layout.list_element, parent, false)
        }

        val name = convertView!!.findViewById(R.id.Row) as TextView

        name.text = boxViewModel?.categoryName.toString()
        return convertView
    }
}