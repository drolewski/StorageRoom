package io.drolewski.storageroom.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Photo")
data class Photo(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "photo_id") val photoId: Int = 0,
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB) val image: ByteArray? = null,
    @ColumnInfo(name = "object_id") val objectThing: Int?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Photo

        if (photoId != other.photoId) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (objectThing != other.objectThing) return false

        return true
    }

    override fun hashCode(): Int {
        var result = photoId
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + (objectThing ?: 0)
        return result
    }
}
