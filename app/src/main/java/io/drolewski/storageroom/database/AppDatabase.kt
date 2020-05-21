package io.drolewski.storageroom.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.drolewski.storageroom.dao.*
import io.drolewski.storageroom.entity.*

@Database(
    entities = [Box::class, Category::class, Localization::class, Object::class,
        ObjectCategoryCrossRef::class, Photo::class], version = 1
)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun boxDAO(): BoxDAO
    abstract fun categoryDAO(): CategoryDAO
    abstract fun localizationDAO(): LocalizationDAO
    abstract fun objectDAO(): ObjectDAO
    abstract fun photoDAO(): PhotoDAO

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context, AppDatabase::class.java, "storage-room.db")
            .build()
    }

}