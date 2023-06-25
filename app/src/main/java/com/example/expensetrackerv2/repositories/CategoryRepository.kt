package com.example.expensetrackerv2.repositories

import com.github.pploszczyca.expensetrackerv2.domain.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAll(): Flow<List<Category>>

    suspend fun insert(categoryEntity: Category)

    suspend fun delete(categoryEntity: Category)

    suspend fun update(categoryEntity: Category)
}