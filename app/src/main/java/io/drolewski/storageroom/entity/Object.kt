package io.drolewski.storageroom.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Object"
)
data class Object(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "object_id") val objectId: Int,
    @ColumnInfo(name = "object_name") var objectName: String,
    @ColumnInfo(name = "ean") val ean: String?,
    @ColumnInfo(name = "commentary") var commentary: String,
    @ColumnInfo(name = "box_id") var boxId: Int?
)