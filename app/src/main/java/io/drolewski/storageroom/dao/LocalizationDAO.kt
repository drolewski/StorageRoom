package io.drolewski.storageroom.dao

import androidx.room.*
import io.drolewski.storageroom.entity.Localization
import io.drolewski.storageroom.entity.LocalizationWithBoxes

@Dao
abstract class LocalizationDAO : GenericDAO<Localization>() {

    @Query("SELECT * FROM Localization")
    abstract fun getAll(): List<Localization>

    @Query("SELECT * FROM Localization where localization_name = :entityName")
    abstract fun getByName(entityName: String): List<Localization>
}