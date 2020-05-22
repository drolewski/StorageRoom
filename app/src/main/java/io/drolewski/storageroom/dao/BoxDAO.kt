package io.drolewski.storageroom.dao

import androidx.room.*
import io.drolewski.storageroom.entity.Box
import io.drolewski.storageroom.entity.BoxWithObjectsAndCategories
import io.drolewski.storageroom.entity.BoxWithPhoto

@Dao
abstract class BoxDAO : GenericDAO<Box>() {

    @Query("SELECT * FROM Box")
    abstract fun getAll(): List<Box>

    @Query("SELECT * FROM Box where box_id = :entityId")
    abstract fun getById(entityId: Int): Box
}