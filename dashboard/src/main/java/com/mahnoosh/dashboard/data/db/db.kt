package com.mahnoosh.dashboard.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mahnoosh.dashboard.data.models.local.entity.CategoryEntity

@Database(
    entities = [CategoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DashboardDataBase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}