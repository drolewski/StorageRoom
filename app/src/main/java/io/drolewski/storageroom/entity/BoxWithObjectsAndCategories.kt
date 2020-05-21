package io.drolewski.storageroom.entity

import androidx.room.Embedded
import androidx.room.Relation

data class BoxWithObjectsAndCategories(
    @Embedded var box: Box,
    @Relation(
        entity = Object::class,
        parentColumn = "box_id",
        entityColumn = "box_id"
    )
    var objects: List<ObjectWithCategory>
)