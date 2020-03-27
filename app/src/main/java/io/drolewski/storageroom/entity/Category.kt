package io.drolewski.storageroom.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class Category(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "category_id") val categoryId: Int = 0,
    @ColumnInfo(name = "category_name") val categoryName: String
)