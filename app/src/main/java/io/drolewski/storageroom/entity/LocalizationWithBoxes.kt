package io.drolewski.storageroom.entity

import androidx.room.Embedded
import androidx.room.Relation

data class LocalizationWithBoxes(
    @Embedded
    var localization: Localization,
    @Relation(
        parentColumn = "localization_name",
        entityColumn = "localization_name"
    )
    var boxes: List<Box>
)