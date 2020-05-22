package io.drolewski.storageroom.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Localization"
)
data class Localization(
    @PrimaryKey @ColumnInfo(name = "localization_name") val localizationName: String
)