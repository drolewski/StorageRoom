package io.drolewski.storageroom.dao

import androidx.room.*
import io.drolewski.storageroom.entity.Category
import io.drolewski.storageroom.entity.CategoryWithObject

@Dao
abstract class CategoryDAO : GenericDAO<Category>(){

    @Query("SELECT * FROM Category")
    abstract fun getAll(): List<Category>

    @Query("SELECT * FROM Category where category_id = :entityId")
    abstract fun getById(entityId: Int): Category
}