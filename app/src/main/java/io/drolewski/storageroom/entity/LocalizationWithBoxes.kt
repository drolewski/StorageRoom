package io.drolewski.storageroom.entity

import androidx.room.Embedded
import androidx.room.Relation

class LocalizationWithBoxes(
    @Embedded val localization: Localization,
    @Relation(
        parentColumn = "localization_id",
        entityColumn = "localization_id"
    )
    val boxes: List<Box>
)