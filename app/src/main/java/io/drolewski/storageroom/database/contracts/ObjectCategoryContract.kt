package io.drolewski.storageroom.database.contracts

import android.provider.BaseColumns

object ObjectCategoryContract {
    object ObjectCategory : BaseColumns {
        const val TABLE_NAME = "object_category"
        const val COLUMN_NAME_OBJECT_ID = "object_id"
        const val COLUMN_NAME_CATEGORY_ID = "category_id"
    }
}