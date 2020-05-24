package io.drolewski.storageroom.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import io.drolewski.storageroom.activities.R
import io.drolewski.storageroom.model.BoxViewModel

public class BoxElementAdapter(context: Context?, users: List<BoxViewModel?>?) :
    ArrayAdapter<BoxViewModel>(context!!, 0, users!!) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val boxViewModel: BoxViewModel? = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.box_element, parent, false)
        }

        val name = convertView!!.findViewById<View>(R.id.boxName) as TextView
        val localization = convertView!!.findViewById<View>(R.id.boxLocalization) as TextView
        val qrCode = convertView!!.findViewById<View>(R.id.boxQRcode) as TextView
        val photo = convertView!!.findViewById<View>(R.id.boxPhotoEle) as ImageView

        name.text = boxViewModel?.name
        localization.text = boxViewModel?.localization
        qrCode.text = boxViewModel?.qrCode
        photo.setImageBitmap(boxViewModel?.photo)
        return convertView
    }
}