package io.drolewski.storageroom.dao

import androidx.room.*
import io.drolewski.storageroom.entity.Localization
import io.drolewski.storageroom.entity.LocalizationWithBoxes

@Dao
interface LocalizationDAO {
    @Insert(entity = Localization::class)
    fun insertLocalization(vararg localization: Localization)

    @Update(entity = Localization::class)
    fun updateLocalization(vararg localization: Localization)

    @Delete(entity = Localization::class)
    fun deleteLocalization(vararg localization: Localization)

    @Transaction
    @Query("SELECT * FROM Localization")
    fun getLocalizationWithBoxes(): List<LocalizationWithBoxes>
}