package io.drolewski.storageroom.model

import android.graphics.Bitmap

class BoxViewModel(
    val name: String,
    val localization: String,
    val qrCode: String,
    val photo: Bitmap?
)