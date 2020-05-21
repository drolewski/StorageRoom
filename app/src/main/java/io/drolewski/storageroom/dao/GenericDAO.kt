package io.drolewski.storageroom.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
abstract class GenericDAO<T> {
    @Insert()
    abstract fun add(obj: T)

    @Insert()
    abstract fun add(obj: List<T>)

    @Update()
    abstract fun update(obj: T)

    @Update()
    abstract fun update(obj: List<T>)

    @Delete()
    abstract fun delete(obj: T)

    @Delete()
    abstract fun delete(obj: List<T>)
}