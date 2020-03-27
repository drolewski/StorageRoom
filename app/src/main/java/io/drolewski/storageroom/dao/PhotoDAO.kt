package io.drolewski.storageroom.dao

import androidx.room.*
import io.drolewski.storageroom.entity.Photo

@Dao
interface PhotoDAO {
    @Insert(entity = Photo::class)
    fun insertPhoto(vararg photo: Photo)

    @Update(entity = Photo::class)
    fun updatePhoto(vararg photo: Photo)

    @Delete(entity = Photo::class)
    fun deletePhoto(vararg photo: Photo)

    @Transaction
    @Query("SELECT * FROM Photo")
    fun getAllPhotos(): List<Photo>
}