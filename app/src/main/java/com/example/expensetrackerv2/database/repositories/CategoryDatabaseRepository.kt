package com.example.expensetrackerv2.database.repositories

import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.database.models.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryDatabaseRepository @Inject constructor(private val expenseDao: ExpenseDao) :
    CategoryRepository {
    private val typeOfExpenseLiveData = expenseDao.getAllCategories()

    override fun getAllCategories(): Flow<List<Category>> = typeOfExpenseLiveData

    override suspend fun insertCategory(category: Category) =
        expenseDao.insertAllCategories(category)

    override suspend fun deleteCategory(category: Category) =
        expenseDao.deleteTypeOfExpense(category)

    override suspend fun updateCategory(category: Category) =
        expenseDao.updateTypeOfExpense(category)
}