package com.example.expensetrackerv2.repositories

import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.models.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryDatabaseRepository @Inject constructor(private val expenseDao: ExpenseDao) :
    CategoryRepository {
    private val typeOfExpenseLiveData = expenseDao.getAllCategories()

    override fun getAllCategories(): Flow<List<CategoryEntity>> = typeOfExpenseLiveData

    override suspend fun insertCategory(categoryEntity: CategoryEntity) =
        expenseDao.insertAllCategories(categoryEntity)

    override suspend fun deleteCategory(categoryEntity: CategoryEntity) =
        expenseDao.deleteTypeOfExpense(categoryEntity)

    override suspend fun updateCategory(categoryEntity: CategoryEntity) =
        expenseDao.updateTypeOfExpense(categoryEntity)
}