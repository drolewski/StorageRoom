package io.drolewski.storageroom.entity

import androidx.room.Embedded
import androidx.room.Relation

data class BoxWithPhoto (
    @Embedded
    var box: Box,
    @Relation(
        parentColumn = "box_photo_id",
        entityColumn = "photo_id"
    )
    var photo: List<Photo>
)
