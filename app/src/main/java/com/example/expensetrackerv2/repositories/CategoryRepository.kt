package com.example.expensetrackerv2.repositories

import com.example.expensetrackerv2.models.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAll(): Flow<List<CategoryEntity>>

    suspend fun insert(vararg categoryEntity: CategoryEntity)

    suspend fun delete(categoryEntity: CategoryEntity)

    suspend fun update(categoryEntity: CategoryEntity)
}