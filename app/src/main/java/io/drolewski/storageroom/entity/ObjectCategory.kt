package io.drolewski.storageroom.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class ObjectWithCategory(
    @Embedded val objectThing: Object,
    @Relation(
        parentColumn = "object_id",
        entityColumn = "category_id",
        associateBy = Junction(ObjectCategoryCrossRef::class)
    )
    val categories: List<Category>
)

data class CategoryWithObject(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "object_id",
        associateBy = Junction(ObjectCategoryCrossRef::class)
    )
    val objects: List<Object>
)