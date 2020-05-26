package io.drolewski.storageroom.database


import android.content.Context
import android.os.Environment
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.drolewski.storageroom.dao.*
import io.drolewski.storageroom.entity.*
import java.io.File
import java.io.FileOutputStream

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
    abstract fun categoryObjectDAO(): CategoryObjectDAO

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

    fun exportDatabase(context: Context){
        Toast.makeText(context, "/storage/self/primary" + "/Download/" + "backup_" + "storage-room.db" + "/Download/" + "backup_", LENGTH_SHORT).show()
        try{
            copyDataFromOneToAnother(context.getDatabasePath("storage-room.db").path, "/storage/self/primary" + "/Download/" + "backup_" + "storage-room.db")
            copyDataFromOneToAnother(context.getDatabasePath("storage-room.db" + "-shm").path, "/storage/self/primary" + "/Download/" + "backup_" + "storage-room.db" + "-shm")
            copyDataFromOneToAnother(context.getDatabasePath("storage-room.db" + "-wal").path, "/storage/self/primary"+ "/Download/" + "backup_" + "storage-room.db" + "-wal")
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun importDatabaseFile(context: Context) {
        try {
            copyDataFromOneToAnother("/storage/self/primary" + "/Download/" + "backup_" + "storage-room.db", context.getDatabasePath("box-evidence.db").path)
            copyDataFromOneToAnother("/storage/self/primary" + "/Download/" + "backup_" + "storage-room.db" + "-shm", context.getDatabasePath("storage-room.db" + "-shm").path)
            copyDataFromOneToAnother("/storage/self/primary"+ "/Download/" + "backup_" + "storage-room.db" + "-wal", context.getDatabasePath("storage-room.db" + "-wal").path)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun copyDataFromOneToAnother(fromPath: String, toPath: String) {
        val inStream = File(fromPath).inputStream()
        val outStream = FileOutputStream(toPath)

        inStream.use { input ->
            outStream.use { output ->
                input.copyTo(output)
            }
        }
    }

}