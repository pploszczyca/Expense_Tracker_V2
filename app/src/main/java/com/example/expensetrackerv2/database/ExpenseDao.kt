package com.example.expensetrackerv2.database

import androidx.room.*
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.models.Category
import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ExpenseDao {
    //QUERIES
    @Query("SELECT * FROM expense ORDER BY date DESC")
    abstract fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM category")
    abstract fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM ExpenseWithCategory ORDER BY date DESC")
    abstract fun getAllExpenseWithCategory(): Flow<List<ExpenseWithCategory>>

    @Query("SELECT * FROM expense WHERE id = :expenseID")
    abstract fun getExpense(expenseID: Int): Expense

    @Query("SELECT * FROM ExpenseWithCategory WHERE id = :expenseID")
    abstract fun getExpenseWithItsType(expenseID: Int): Flow<ExpenseWithCategory?>

    // INSERTS
    @Insert
    abstract suspend fun insertAllExpenses(vararg expenses: Expense)

    @Insert
    abstract suspend fun insertAllCategories(vararg expenses: Category)

    // UPDATE
    @Update
    abstract suspend fun updateExpense(expense: Expense)

    @Update
    abstract suspend fun updateTypeOfExpense(category: Category)

    // DELETES
    @Query("DELETE FROM Expense WHERE id = :id")
    abstract suspend fun deleteExpenseByID(id: Int)

    @Query("DELETE FROM EXPENSE")
    abstract suspend fun deleteAllExpenses()

    @Delete
    abstract suspend fun deleteExpense(expense: Expense)

    suspend fun deleteExpense(expenseWithCategory: ExpenseWithCategory) =
        deleteExpenseByID(expenseWithCategory.id)

    @Delete
    abstract suspend fun deleteTypeOfExpense(vararg category: Category)
}