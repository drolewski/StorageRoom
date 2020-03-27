package io.drolewski.storageroom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.drolewski.storageroom.dao.*
import io.drolewski.storageroom.entity.*

@Database(
    entities = [
        Box::class, Category::class,
        Localization::class, Object::class,
        ObjectCategoryCrossRef::class, Photo::class],
    version = 1
)
abstract class DatabaseStorageRoom : RoomDatabase() {
    abstract fun boxDAO(): BoxDAO
    abstract fun categoryDAO(): CategoryDAO
    abstract fun localizationDAO(): LocalizationDAO
    abstract fun objectDAOA(): ObjectDAO
    abstract fun photoDAO(): PhotoDAO
}