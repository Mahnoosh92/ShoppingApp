package com.mahnoosh.dashboard.data.datasource.local.category

import com.mahnoosh.dashboard.data.db.CategoryDao
import com.mahnoosh.dashboard.data.models.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultCategoryLocalDataSource(private val dao: CategoryDao) : CategoryLocalDataSource {
    override fun getCategories(): Flow<List<CategoryEntity>> = dao.getCategories()

    override fun updateCategories(categories: List<CategoryEntity>?): Flow<Unit> = flow {
        dao.insertAll(categories)
        emit(Unit)
    }

    override fun clear(): Flow<Unit> = flow {
        dao.clear()
        emit(Unit)
    }
}