package com.example.expensetrackerv2.database

import androidx.room.*
import com.github.pploszczyca.expensetrackerv2.database.models.CategoryEntity
import com.github.pploszczyca.expensetrackerv2.database.models.ExpenseEntity
import com.github.pploszczyca.expensetrackerv2.database.models.view_models.ExpenseWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ExpenseDao {
    //QUERIES
    @Query("SELECT * FROM expense ORDER BY date DESC")
    abstract fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM category")
    abstract fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM ExpenseWithCategory ORDER BY date DESC")
    abstract fun getAllExpenseWithCategory(): Flow<List<ExpenseWithCategory>>

    @Query("SELECT * FROM expense WHERE id = :expenseID")
    abstract fun getExpense(expenseID: Int): Flow<ExpenseEntity>

    @Query("SELECT * FROM ExpenseWithCategory WHERE id = :expenseID")
    abstract fun getExpenseWithItsType(expenseID: Int): Flow<ExpenseWithCategory>

    // INSERTS
    @Insert
    abstract suspend fun insertAllExpenses(vararg expenses: ExpenseEntity)

    @Insert
    abstract suspend fun insertAllCategories(vararg expenses: CategoryEntity)

    // UPDATE
    @Update
    abstract suspend fun updateExpense(expense: ExpenseEntity)

    @Update
    abstract suspend fun updateTypeOfExpense(categoryEntity: CategoryEntity)

    // DELETES
    @Query("DELETE FROM Expense WHERE id = :id")
    abstract suspend fun deleteExpenseByID(id: Int)

    @Query("DELETE FROM EXPENSE")
    abstract suspend fun deleteAllExpenses()

    @Delete
    abstract suspend fun deleteExpense(expense: ExpenseEntity)

    suspend fun deleteExpense(expenseWithCategory: ExpenseWithCategory) =
        deleteExpenseByID(expenseWithCategory.id)

    @Delete
    abstract suspend fun deleteTypeOfExpense(vararg categoryEntity: CategoryEntity)
}