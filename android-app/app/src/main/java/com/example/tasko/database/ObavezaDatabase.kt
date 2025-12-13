package com.example.tasko.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(ObavezaRoom::class), version = 1, exportSchema = false)
abstract class ObavezaDatabase : RoomDatabase() {
    abstract fun obavezaDao(): ObavezaDao

    companion object {
        private var INSTANCE: ObavezaDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ObavezaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ObavezaDatabase::class.java,
                    "obaveza_database"
                ).addCallback(ObavezaDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                return@synchronized instance
            }
        }
    }

    private class ObavezaDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch { populateDatabase(database.obavezaDao()) }
            }
        }

        suspend fun populateDatabase(obavezaDao: ObavezaDao) {
            obavezaDao.deleteAll()
        }
    }
}
