package io.drolewski.storageroom.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ObjectWithPhotos(
    @Embedded
    var objectThing: Object,
    @Relation(
        parentColumn = "object_id",
        entityColumn = "object_id"
    )
    var photos: List<Photo>
)