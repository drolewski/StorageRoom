package io.drolewski.storageroom.dao

import androidx.room.*
import io.drolewski.storageroom.entity.Photo

@Dao
abstract class PhotoDAO : GenericDAO<Photo>() {

    @Query("SELECT * FROM Photo")
    abstract fun getAll(): List<Photo>

    @Query("SELECT * FROM Photo where photo_id = :entityId")
    abstract fun getById(entityId: Int): List<Photo>
}