package com.training.jetdemo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.training.jetdemo.data.local.db.dao.RepoDao
import com.training.jetdemo.data.local.db.entity.RepoEntity
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        RepoEntity::class
    ],
    exportSchema = false,
    version = 3
)
@TypeConverters(Converter::class)
abstract class DatabaseService : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}