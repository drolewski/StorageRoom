package io.drolewski.storageroom.dao

import androidx.room.*
import io.drolewski.storageroom.entity.Object
import io.drolewski.storageroom.entity.ObjectWithCategory
import io.drolewski.storageroom.entity.ObjectWithPhotos

@Dao
interface ObjectDAO {
    @Insert(entity = Object::class)
    fun insertObject(vararg objectThing: Object)

    @Update(entity = Object::class)
    fun updateObject(vararg objectThing: Object)

    @Delete(entity = Object::class)
    fun deleteObject(vararg objectThing: Object)

    @Transaction
    @Query("SELECT * FROM Object")
    fun getObjectWithCategory(): List<ObjectWithCategory>

    @Transaction
    @Query("SELECT * FROM Object")
    fun getObjectWithPhotos(): List<ObjectWithPhotos>
}