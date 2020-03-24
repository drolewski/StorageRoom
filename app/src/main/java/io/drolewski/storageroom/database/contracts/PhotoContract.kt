package io.drolewski.storageroom.database.contracts

import android.provider.BaseColumns

object PhotoContract {
    object Photo : BaseColumns {
        const val TABLE_NAME = "photo"
        const val COLUMN_NAME_IMAGE = "image"
        const val COLUMN_NAME_OBJECT_ID = "object_id"
    }
}