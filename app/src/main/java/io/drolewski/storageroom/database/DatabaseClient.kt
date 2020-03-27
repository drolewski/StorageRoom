package io.drolewski.storageroom.database

import android.content.Context
import androidx.room.Room

class DatabaseClient(
    var context: Context,
    var appDatabase: DatabaseStorageRoom =
        Room.databaseBuilder(context, DatabaseStorageRoom::class.java, "StorageRoom.db")
            .build()
)