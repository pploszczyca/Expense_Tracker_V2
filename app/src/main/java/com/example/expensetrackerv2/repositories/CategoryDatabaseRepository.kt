package com.example.expensetrackerv2.repositories

import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.models.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryDatabaseRepository @Inject constructor(private val expenseDao: ExpenseDao) :
    CategoryRepository {
    private val typeOfExpenseLiveData = expenseDao.getAllCategories()

    override fun getAll(): Flow<List<CategoryEntity>> = typeOfExpenseLiveData

    override suspend fun insert(vararg categoryEntity: CategoryEntity) =
        categoryEntity.forEach { expenseDao.insertAllCategories(it) }

    override suspend fun delete(categoryEntity: CategoryEntity) =
        expenseDao.deleteTypeOfExpense(categoryEntity)

    override suspend fun update(categoryEntity: CategoryEntity) =
        expenseDao.updateTypeOfExpense(categoryEntity)
}