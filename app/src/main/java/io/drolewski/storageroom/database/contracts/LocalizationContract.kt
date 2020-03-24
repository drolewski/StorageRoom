package io.drolewski.storageroom.database.contracts

import android.provider.BaseColumns

object LocalizationContract {
    object Localization : BaseColumns {
        const val TABLE_NAME = "localization"
        const val COLUMN_NAME_NAME = "name"
    }
}