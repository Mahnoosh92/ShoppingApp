package com.mahnoosh.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mahnoosh.core.data.db.dao.TestDao
import com.mahnoosh.core.data.db.models.TestEntity

@Database(
    entities = [TestEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun testDao(): TestDao
}