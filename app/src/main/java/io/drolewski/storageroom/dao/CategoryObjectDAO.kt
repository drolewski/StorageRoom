package io.drolewski.storageroom.dao

import androidx.room.Dao
import androidx.room.Query
import io.drolewski.storageroom.entity.ObjectCategoryCrossRef

@Dao
abstract class CategoryObjectDAO : GenericDAO<ObjectCategoryCrossRef>() {

    @Query("SELECT * FROM Object_category")
    abstract fun getAll(): Array<ObjectCategoryCrossRef>

    @Query("SELECT * FROM Object_category WHERE category_id = :categoryId")
    abstract fun getByCategoryID(categoryId: Int) : Array<ObjectCategoryCrossRef>

    @Query("SELECT * FROM Object_category WHERE object_id = :objectId")
    abstract fun getByObjectId(objectId: Int) : Array<ObjectCategoryCrossRef>
}