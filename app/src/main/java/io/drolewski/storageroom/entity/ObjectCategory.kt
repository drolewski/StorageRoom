package io.drolewski.storageroom.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class ObjectWithCategory {
    @Embedded
    lateinit var objectThing: Object
    @Relation(
        parentColumn = "object_id",
        entityColumn = "category_id",
        associateBy = Junction(ObjectCategoryCrossRef::class)
    )
    lateinit var categories: List<Category>
}

class CategoryWithObject() {
    @Embedded
    lateinit var category: Category
    @Relation(
        parentColumn = "category_id",
        entityColumn = "object_id",
        associateBy = Junction(ObjectCategoryCrossRef::class)
    )
    lateinit var objects: List<Object>
}