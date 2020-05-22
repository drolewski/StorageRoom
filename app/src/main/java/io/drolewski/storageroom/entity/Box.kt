package io.drolewski.storageroom.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Box"
)
data class Box(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "box_id") val boxId: Int,
    @ColumnInfo(name = "box_name") val boxName: String,
    @ColumnInfo(name = "localization_name") val localization: String,
    @ColumnInfo(name = "commentary") val boxCommentary: String,
    @ColumnInfo(name = "qr_code") val qrCode: String,
    @ColumnInfo(name = "box_photo_id") val photo: Int?
)
