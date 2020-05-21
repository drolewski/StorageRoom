package io.drolewski.storageroom.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class ObjectWithCategory(
    @Embedded
    var objectThing: Object,
    @Relation(
        parentColumn = "object_id",
        entityColumn = "category_id",
        associateBy = Junction(ObjectCategoryCrossRef::class)
    )
    var categories: List<Category>
)

data class CategoryWithObject(
    @Embedded
    var category: Category,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "object_id",
        associateBy = Junction(ObjectCategoryCrossRef::class)
    )
    var objects: List<Object>
)