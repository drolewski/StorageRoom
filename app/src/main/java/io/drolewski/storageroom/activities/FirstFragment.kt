package io.drolewski.storageroom.activities

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.drolewski.storageroom.database.AppDatabase
import io.drolewski.storageroom.entity.Localization
import kotlinx.android.synthetic.main.first_fragment.*
import java.io.File


class FirstFragment : Fragment() {

    var selectedfile = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val error = AlertDialog.Builder(this.context)
            .setMessage("Invalid data in form, please try again.")
            .setPositiveButton("OK", null)



        button_next.setOnClickListener {
            try {
                if (name.text.toString() == "") throw Exception()
                Thread {
                    val db = this.context?.let { it1 -> AppDatabase(it1) }
                    if (db != null) {
                        db.localizationDAO().add(Localization(name.text.toString()))
                    } else {
                        throw Exception()
                    }
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }.start()
            } catch (exc: Exception) {
                error.show()
            }
        }

        button_import.setOnClickListener {
            //import
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 123)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == RESULT_OK) {
            selectedfile = data!!.data.toString()
            File("//storage//self//primary//Documents//storage-room-export.db").let { sourceFile ->
                val file =
                    File("//data//data//io.drolewski.storageroom//databases//storage-room.db")
                if (file.exists()) {
                    file.delete()
                }
                sourceFile.copyTo(file)
            }
            File("//storage//self//primary//Documents//storage-room-export.db-shm").let { sourceFile ->
                val file =
                    File("//data//data//io.drolewski.storageroom//databases//storage-room.db-shm")
                if (file.exists()) {
                    file.delete()
                }
                sourceFile.copyTo(file)
            }
            File("//storage//self//primary//Documents//storage-room-export.db-wal").let { sourceFile ->
                val file =
                    File("//data//data//io.drolewski.storageroom//databases//storage-room.db-wal")
                if (file.exists()) {
                    file.delete()
                }
                sourceFile.copyTo(file)
            }
            val intentToStart = Intent(
                this.context,
                MenuStart::class.java
            )
            startActivity(intentToStart)
        }
    }
}