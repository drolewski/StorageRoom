package io.drolewski.storageroom.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ObjectWithPhotos(
    @Embedded val objectThing: Object,
    @Relation(
        parentColumn = "object_id",
        entityColumn = "object_id"
    )
    val photos: List<Photo>
)