package io.drolewski.storageroom.entity

import androidx.room.*

@Entity(
    tableName = "Object",
    indices = [Index(value = ["object_name"])]
)
data class Object(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "object_id") val objectId: Int,
    @ColumnInfo(name = "object_name") val objectName: String,
    @ColumnInfo(name = "ean") val ean: String?,
    @ColumnInfo(name = "commentary") val commentary: String,
    @ColumnInfo(name = "box_id") val boxId: Int?
)