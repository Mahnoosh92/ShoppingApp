package com.mahnoosh.dashboard.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahnoosh.dashboard.data.models.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<CategoryEntity>?)

    @Query("select * from categories")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Query("DELETE FROM categories")
    suspend fun clear()
}