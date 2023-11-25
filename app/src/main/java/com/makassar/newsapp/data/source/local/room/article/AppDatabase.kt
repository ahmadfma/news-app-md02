package com.makassar.newsapp.data.source.local.room.article

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ArticleEntity::class],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            if(INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "news_app_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(AppDatabaseCallback())
                        .build()
                }
            }
            return INSTANCE as AppDatabase
        }

        private class AppDatabaseCallback : RoomDatabase.Callback()
    }

}