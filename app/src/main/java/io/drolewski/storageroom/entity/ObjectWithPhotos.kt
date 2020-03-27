package io.drolewski.storageroom.entity

import androidx.room.Embedded
import androidx.room.Relation

class ObjectWithPhotos {
    @Embedded
    lateinit var objectThing: Object
    @Relation(
        parentColumn = "object_id",
        entityColumn = "object_id"
    )
    lateinit var photos: List<Photo>
}