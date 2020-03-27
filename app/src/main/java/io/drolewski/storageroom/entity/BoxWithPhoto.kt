package io.drolewski.storageroom.entity

import androidx.room.Embedded
import androidx.room.Relation

class BoxWithPhoto {
    @Embedded
    lateinit var box: Box
    @Relation(
        parentColumn = "box_photo_id",
        entityColumn = "photo_id"
    )
    lateinit var photo: List<Photo>
}
