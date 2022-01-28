package com.example.testapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapplication.data.database.dao.RepoDao
import com.example.testapplication.domain.entity.Repo


@Database(
    version = 8, exportSchema = false,
    entities = [Repo::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRepoDao(): RepoDao
}