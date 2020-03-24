package io.drolewski.storageroom.entity

import androidx.room.Embedded
import androidx.room.Relation

data class BoxWithPhoto(
    @Embedded val box: Box,
    @Relation(
        parentColumn = "box_photo_id",
        entityColumn = "photo_id"
    )
    val photo: Photo?
)
