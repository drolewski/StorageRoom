package io.drolewski.storageroom.entity

import androidx.room.Embedded
import androidx.room.Relation

class BoxWithObjectsAndCategories {
    @Embedded
    lateinit var box: Box
    @Relation(
        entity = Object::class,
        parentColumn = "box_id",
        entityColumn = "box_id"
    )
    lateinit var objects: List<ObjectWithCategory>
}