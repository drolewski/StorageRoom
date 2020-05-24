package io.drolewski.storageroom.adapters

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import io.drolewski.storageroom.activities.R
import io.drolewski.storageroom.model.ObjectViewModel

public class ItemElementAdapter(context: Context?, users: List<ObjectViewModel?>?) :  ArrayAdapter<ObjectViewModel>(context!!, 0, users!!) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val objectViewModel: ObjectViewModel? = getItem(position)
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_element, parent, false)
        }

        val name = convertView!!.findViewById<View>(R.id.itemRowName) as TextView
        val ean = convertView!!.findViewById<View>(R.id.itemRowEAN) as TextView
        val commentary = convertView!!.findViewById<View>(R.id.itemRowCommentary) as TextView
        val photo = convertView!!.findViewById<View>(R.id.imageView) as ImageView

        name.text = objectViewModel?.name
        ean.text = objectViewModel?.ean
        commentary.text = objectViewModel?.commentary
        photo.setImageBitmap(objectViewModel?.photo)
        return convertView
    }

}