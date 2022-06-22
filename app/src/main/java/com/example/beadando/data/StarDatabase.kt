package com.example.beadando.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Star::class], version = 3, exportSchema = false)
abstract class StarDatabase : RoomDatabase() {

    abstract fun starDao(): StarDao

    companion object {
        @Volatile
        private var INSTANCE: StarDatabase? = null

        fun getDatabase(context: Context): StarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StarDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}