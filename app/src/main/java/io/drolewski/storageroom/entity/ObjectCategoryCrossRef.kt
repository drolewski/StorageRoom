package io.drolewski.storageroom.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "Object_category",
    primaryKeys = ["object_id", "category_id"]
)
data class ObjectCategoryCrossRef(
    @ColumnInfo(name = "object_id") val objectId: Int,
    @ColumnInfo(name = "category_id") val categoryId: Int
)