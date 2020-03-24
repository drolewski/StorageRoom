package io.drolewski.storageroom.database.contracts

import android.provider.BaseColumns

object BoxContract {
    object Box : BaseColumns {
        const val TABLE_NAME = "box"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_COMMENTARY = "commentary"
        const val COLUMN_NAME_QR_CODE = "qr_code"
        const val COLUMN_NAME_PHOTO_ID = "photo_id"
        const val COLUMN_NAME_LOCALIZATION_ID = "localization_id"
    }
}