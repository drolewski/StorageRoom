package io.drolewski.storageroom.dao

import androidx.room.*
import io.drolewski.storageroom.entity.Object
import io.drolewski.storageroom.entity.ObjectWithCategory
import io.drolewski.storageroom.entity.ObjectWithPhotos

@Dao
abstract class ObjectDAO : GenericDAO<Object>() {

    @Query("SELECT * FROM Object")
    abstract fun getAll(): List<Object>

    @Query("SELECT * FROM Object where object_id = :entityId")
    abstract fun getById(entityId: Int): List<Object>

    @Query("SELECT * FROM Object JOIN Photo ON Object.object_id = Photo.object_id")
    abstract fun getAllWithPhotos(): List<ObjectWithPhotos>
}