package io.drolewski.storageroom.database.contracts

import android.provider.BaseColumns

object CategoryContract {
    object Category : BaseColumns {
        const val TABLE_NAME = "category"
        const val COLUMN_NAME_NAME = "name"
    }
}