package io.drolewski.storageroom.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import io.drolewski.storageroom.database.AppDatabase
import io.drolewski.storageroom.entity.Localization
import kotlinx.android.synthetic.main.first_fragment.*

class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.second_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_finish).setOnClickListener{
            val activity2Intent = Intent(
                this.context,
                MenuStart::class.java
            )
            startActivity(activity2Intent)
        }
    }
}