package io.drolewski.storageroom.entity

import androidx.room.Embedded
import androidx.room.Relation

class LocalizationWithBoxes {
    @Embedded
    lateinit var localization: Localization
    @Relation(
        parentColumn = "localization_name",
        entityColumn = "localization_name"
    )
    lateinit var boxes: List<Box>
}