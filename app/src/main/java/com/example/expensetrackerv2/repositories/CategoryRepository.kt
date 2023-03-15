package com.example.expensetrackerv2.repositories

import com.example.expensetrackerv2.models.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<List<CategoryEntity>>

    suspend fun insertCategory(categoryEntity: CategoryEntity)

    suspend fun deleteCategory(categoryEntity: CategoryEntity)

    suspend fun updateCategory(categoryEntity: CategoryEntity)
}