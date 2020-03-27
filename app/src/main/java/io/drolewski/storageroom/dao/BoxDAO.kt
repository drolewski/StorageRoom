package io.drolewski.storageroom.dao

import androidx.room.*
import io.drolewski.storageroom.entity.Box
import io.drolewski.storageroom.entity.BoxWithObjectsAndCategories
import io.drolewski.storageroom.entity.BoxWithPhoto

@Dao
interface BoxDAO {
    @Insert(entity = Box::class)
    fun insertBox(vararg box: Box)

    @Update(entity = Box::class)
    fun updateBox(vararg box: Box)

    @Delete(entity = Box::class)
    fun deleteBox(vararg box: Box)

    @Transaction
    @Query("SELECT * FROM Box")
    fun getBoxWithPhoto(): List<BoxWithPhoto>

    @Transaction
    @Query("SELECT * FROM Box")
    fun getBoxWithObjectsAndCategories(): List<BoxWithObjectsAndCategories>
}