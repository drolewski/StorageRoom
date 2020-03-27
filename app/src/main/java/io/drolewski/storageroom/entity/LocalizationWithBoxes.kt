package io.drolewski.storageroom.entity

import androidx.room.Embedded
import androidx.room.Relation

class LocalizationWithBoxes(
    @Embedded val localization: Localization,
    @Relation(
        parentColumn = "localization_name",
        entityColumn = "localization_name"
    )
    val boxes: List<Box>
)