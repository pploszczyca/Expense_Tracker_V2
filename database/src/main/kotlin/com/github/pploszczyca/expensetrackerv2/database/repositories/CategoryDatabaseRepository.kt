package com.github.pploszczyca.expensetrackerv2.database.repositories

import com.github.pploszczyca.expensetrackerv2.database.database.ExpenseDao
import com.github.pploszczyca.expensetrackerv2.database.repositories.mappers.CategoryMapper
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class CategoryDatabaseRepository(
    private val expenseDao: ExpenseDao,
    private val categoryMapper: CategoryMapper = CategoryMapper(),
) : CategoryRepository {
    override fun getAll(): Flow<List<Category>> =
        expenseDao.getAllCategories().map { it.map(categoryMapper::toDomainModel) }

    override suspend fun insert(category: Category) {
        expenseDao.insertAllCategories(category.let(categoryMapper::toDatabaseModel))
    }

    override suspend fun delete(category: Category) {
        expenseDao.deleteTypeOfExpense(category.let(categoryMapper::toDatabaseModel))
    }

    override suspend fun update(category: Category) {
        expenseDao.updateTypeOfExpense(category.let(categoryMapper::toDatabaseModel))
    }
}