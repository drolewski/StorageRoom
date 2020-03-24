package io.drolewski.storageroom.database.contracts

import android.provider.BaseColumns

object StoredThingContract {
    object StoredThing : BaseColumns {
        const val TABLE_NAME = "objects"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_CODE = "code_ean"
        const val COLUMN_NAME_COMMENTARY = "commentary"
        const val COLUMN_NAME_BOX_ID = "box_id"
    }
}