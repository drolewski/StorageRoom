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

    @Query("SELECT * FROM Box JOIN Photo ON Box.box_photo_id = Photo.photo_id")
    abstract fun getAllWithPhotos(): List<BoxWithPhoto>

    @Query("SELECT * FROM Box where qr_code = :scannerResult")
    abstract fun getByQrCode(scannerResult: String): List<Box>
}