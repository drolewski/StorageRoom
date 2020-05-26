package io.drolewski.storageroom.adapters

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import io.drolewski.storageroom.activities.R
import io.drolewski.storageroom.model.ItemInBoxViewModel
import io.drolewski.storageroom.model.SearchItemViewModel

class SearchItemAdapter(context: Context?, users: List<SearchItemViewModel?>?) :
    ArrayAdapter<SearchItemViewModel>(context!!, 0, users!!) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val searchItemModel: SearchItemViewModel? = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                .inflate(R.layout.activity_search_result, parent, false)
        }

        val name = convertView!!.findViewById(R.id.searchItemName) as TextView
        val photo = convertView!!.findViewById<View>(R.id.searchItemImage) as ImageView

        name.text = searchItemModel?.name
        photo.setImageBitmap(searchItemModel?.photo)
        return convertView
    }
}