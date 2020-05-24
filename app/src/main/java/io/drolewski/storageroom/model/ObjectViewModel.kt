package io.drolewski.storageroom.model

import android.graphics.Bitmap

class ObjectViewModel(
    val name: String,
    val ean: String,
    val commentary: String,
    val photo: Bitmap?
)