package com.github.pploszczyca.expensetrackerv2.usecases.repositories

import com.github.pploszczyca.expensetrackerv2.domain.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observe(): Flow<List<Category>>

    suspend fun getAll(): List<Category>

    suspend fun insert(categoryEntity: Category)

    suspend fun delete(categoryEntity: Category)

    suspend fun update(categoryEntity: Category)
}