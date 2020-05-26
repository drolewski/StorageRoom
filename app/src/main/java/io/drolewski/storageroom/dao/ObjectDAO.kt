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

    @Query("SELECT * FROM Object where box_id = :boxId")
    abstract fun getAllWithBoxId(boxId: Int?): List<Object>

    @Query("SELECT * FROM Object where object_name = :s")
    abstract fun getItemWithName(s: String): List<Object>

    @Query("SELECT * FROM Object JOIN Photo ON Object.object_id = Photo.object_id where upper(object_name) LIKE :searching")
    abstract fun getSearching(searching: String): List<ObjectWithPhotos>

    @Query("SELECT * FROM Object JOIN Photo ON Object.object_id = Photo.object_id where Object.object_id = :objectId")
    abstract fun getByIdWithPhotos(objectId: Int): List<ObjectWithPhotos>

    @Query("SELECT * FROM Object JOIN Photo ON Object.object_id = Photo.object_id where Object.ean = :scannerResult")
    abstract fun getItemByEan(scannerResult: String): List<ObjectWithPhotos>

    @Query("SELECT * FROM Object JOIN Photo ON Object.object_id = Photo.object_id where Object.box_id = :boxId")
    abstract fun getItemWithPhotoByBox(boxId: Int): List<ObjectWithPhotos>
}