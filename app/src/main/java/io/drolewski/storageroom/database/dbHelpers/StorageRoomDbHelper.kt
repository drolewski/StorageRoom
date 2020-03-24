package io.drolewski.storageroom.database.dbHelpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import io.drolewski.storageroom.database.contracts.BoxContract.Box
import io.drolewski.storageroom.database.contracts.CategoryContract.Category
import io.drolewski.storageroom.database.contracts.LocalizationContract.Localization
import io.drolewski.storageroom.database.contracts.ObjectCategoryContract.ObjectCategory
import io.drolewski.storageroom.database.contracts.PhotoContract.Photo
import io.drolewski.storageroom.database.contracts.StoredThingContract.StoredThing

class StorageRoomDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    private val SQL_CREATE_STORED_THING =
        "CREATE TABLE ${StoredThing.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${StoredThing.COLUMN_NAME_NAME} TEXT NOT NULL, " +
                "${StoredThing.COLUMN_NAME_CODE} TEXT DEFAULT NULL, " +
                "${StoredThing.COLUMN_NAME_COMMENTARY} TEXT NOT NULL, " +
                "${StoredThing.COLUMN_NAME_BOX_ID} INTEGER DEFAULT NULL );"

    private val SQL_FOREIGN_KEY_STORED_THING =
        "ALTER TABLE ${StoredThing.TABLE_NAME} ADD CONSTRAINT FK_OBJECT_BOX " +
                "FOREIGN KEY (${StoredThing.COLUMN_NAME_BOX_ID}) " +
                "REFERENCES ${Box.TABLE_NAME} (_id) " +
                "ON UPDATE SET NULL ON DELETE SET NULL ;"

    private val SQL_DELETE_STORED_THING =
        "DROP TABLE IF EXISTS ${StoredThing.TABLE_NAME};"

    private val SQL_CREATE_OBJECT_CATEGORY =
        "CREATE TABLE ${ObjectCategory.TABLE_NAME} (" +
                "${ObjectCategory.COLUMN_NAME_OBJECT_ID} INTEGER, " +
                "${ObjectCategory.COLUMN_NAME_CATEGORY_ID} INTEGER, " +
                "PRIMARY KEY (${ObjectCategory.COLUMN_NAME_OBJECT_ID}, " +
                "${ObjectCategory.COLUMN_NAME_CATEGORY_ID}) );"

    private val SQL_FOREIGN_KEY_OBJECT_CATEGORY_OBJECT =
        "ALTER TABLE ${ObjectCategory.TABLE_NAME} ADD CONSTRAINT FK_CATEGORY_OBJECT " +
                "FOREIGN KEY (${ObjectCategory.COLUMN_NAME_OBJECT_ID}) " +
                "REFERENCES ${StoredThing.TABLE_NAME}  (_id) " +
                "ON UPDATE SET NULL ON DELETE SET NULL ;"

    private val SQL_FOREIGN_KEY_OBJECT_OBJECT_CATEGORY =
        "ALTER TABLE ${ObjectCategory.TABLE_NAME} ADD CONSTRAINT FK_OBJECT_CATEGORY " +
                "FOREIGN KEY (${ObjectCategory.COLUMN_NAME_CATEGORY_ID}) " +
                "REFERENCES ${Category.TABLE_NAME} (_id) " +
                "ON UPDATE SET NULL ON DELETE SET NULL ;"

    private val SQL_DELETE_OBJECT_CATEGORY =
        "DROP TABLE IF EXISTS ${ObjectCategory.TABLE_NAME};"

    private val SQL_CREATE_CATEGORY =
        "CREATE TABLE ${Category.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${Category.COLUMN_NAME_NAME} TEXT NOT NULL );"

    private val SQL_DELETE_CATEGORY =
        "DROP TABLE IF EXISTS ${Category.TABLE_NAME};"

    private val SQL_CREATE_PHOTOS =
        "CREATE TABLE ${Photo.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${Photo.COLUMN_NAME_IMAGE} BLOB NOT NULL, " +
                "${Photo.COLUMN_NAME_OBJECT_ID} INTEGER DEFAULT NULL );"

    private val SQL_FOREIGN_KEY_PHOTO =
        "ALTER TABLE ${Photo.TABLE_NAME} ADD CONSTRAINT FK_PHOTO_OBJECT " +
                "FOREIGN KEY (${Photo.COLUMN_NAME_OBJECT_ID} " +
                "REFERENCES ${StoredThing.TABLE_NAME} (_id) " +
                "ON UPDATE SET NULL ON DELETE SET NULL ;"

    private val SQL_DELETE_PHOTO =
        "DROP TABLE IF EXISTS ${Photo.TABLE_NAME};"

    private val SQL_CREATE_BOX =
        "CREATE TABLE ${Box.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${Box.COLUMN_NAME_NAME} TEXT NOT NULL, " +
                "${Box.COLUMN_NAME_COMMENTARY} TEXT NOT NULL, " +
                "${Box.COLUMN_NAME_QR_CODE} TEXT DEFAULT NULL, " +
                "${Box.COLUMN_NAME_PHOTO_ID} INTEGER DEFAULT NULL, " +
                "${Box.COLUMN_NAME_LOCALIZATION_ID} INTEGER NOT NULL );"

    private val SQL_FOREIGN_KEY_BOX_PHOTO =
        "ALTER TABLE ${Box.TABLE_NAME} ADD CONSTRAINT FK_BOX_PHOTO" +
                "FOREIGN KEY (${Box.COLUMN_NAME_PHOTO_ID}) " +
                "REFERENCES ${Photo.TABLE_NAME} (_id) " +
                "ON UPDATE SET NULL ON DELETE SET NULL ;"

    private val SQL_FOREIGN_KEY_BOX_LOCALIZATION =
        "ALTER TABLE ${Box.TABLE_NAME} ADD CONSTRAINT FK_BOX_LOCALIZATION" +
                "FOREIGN KEY (${Box.COLUMN_NAME_LOCALIZATION_ID}) " +
                "REFERENCES ${Localization.TABLE_NAME} (_id) " +
                "ON UPDATE SET NULL ON DELETE SET NULL ;"

    private val SQL_DELETE_BOX =
        "DROP TABLE IF EXISTS ${Box.TABLE_NAME};"

    private val SQL_CREATE_LOCALIZATION =
        "CREATE TABLE ${Localization.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${Localization.TABLE_NAME} TEXT NOT NULL );"

    private val SQL_DELETE_LOCALIZATION =
        "DROP TABLE IF EXISTS ${Localization.TABLE_NAME};"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_LOCALIZATION)
        db?.execSQL(SQL_CREATE_CATEGORY)
        db?.execSQL(SQL_CREATE_STORED_THING)
        db?.execSQL(SQL_CREATE_OBJECT_CATEGORY)

        db?.execSQL(SQL_FOREIGN_KEY_OBJECT_CATEGORY_OBJECT)
        db?.execSQL(SQL_FOREIGN_KEY_OBJECT_OBJECT_CATEGORY)

        db?.execSQL(SQL_CREATE_PHOTOS)
        db?.execSQL(SQL_FOREIGN_KEY_PHOTO)

        db?.execSQL(SQL_CREATE_BOX)
        db?.execSQL(SQL_FOREIGN_KEY_STORED_THING)
        db?.execSQL(SQL_FOREIGN_KEY_BOX_PHOTO)
        db?.execSQL(SQL_FOREIGN_KEY_BOX_LOCALIZATION)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_STORED_THING)
        db?.execSQL(SQL_DELETE_OBJECT_CATEGORY)
        db?.execSQL(SQL_DELETE_CATEGORY)
        db?.execSQL(SQL_DELETE_PHOTO)
        db?.execSQL(SQL_DELETE_BOX)
        db?.execSQL(SQL_DELETE_LOCALIZATION)
        onCreate(db)
    }

    companion object{
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "StorageRoom.db"
    }
}