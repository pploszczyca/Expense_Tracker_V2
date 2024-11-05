package com.github.pploszczyca.expensetrackerv2.database.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.github.pploszczyca.expensetrackerv2.database.models.CategoryEntity
import com.github.pploszczyca.expensetrackerv2.database.models.ExpenseEntity
import com.github.pploszczyca.expensetrackerv2.database.models.view_models.ExpenseWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class ExpenseDao {
    //QUERIES
    @Query("SELECT * FROM expense ORDER BY date DESC")
    abstract fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM category")
    abstract fun observeCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM category")
    abstract suspend fun getCategories(): List<CategoryEntity>

    @Query("SELECT * FROM ExpenseWithCategory ORDER BY date DESC")
    abstract fun observeExpenseWithCategory(): Flow<List<ExpenseWithCategory>>

    @Query("SELECT * FROM ExpenseWithCategory ORDER BY date DESC")
    abstract suspend fun getExpensesWithCategory(): List<ExpenseWithCategory>

    @Query("SELECT * FROM expense WHERE id = :expenseID")
    abstract fun getExpense(expenseID: String): Flow<ExpenseEntity>

    @Query("SELECT * FROM ExpenseWithCategory WHERE id = :expenseID")
    abstract suspend fun getExpenseWithCategory(expenseID: String): ExpenseWithCategory

    // INSERTS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllExpenses(vararg expenses: ExpenseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllCategories(vararg expenses: CategoryEntity)

    // UPDATE
    @Update
    abstract suspend fun updateExpense(expense: ExpenseEntity)

    @Update
    abstract suspend fun updateTypeOfExpense(categoryEntity: CategoryEntity)

    // DELETES
    @Query("DELETE FROM Expense WHERE id = :id")
    abstract suspend fun deleteExpenseByID(id: String)

    @Query("DELETE FROM EXPENSE")
    abstract suspend fun deleteAllExpenses()

    @Delete
    abstract suspend fun deleteExpense(expense: ExpenseEntity)

    suspend fun deleteExpense(expenseWithCategory: ExpenseWithCategory) =
        deleteExpenseByID(expenseWithCategory.id.toString())

    @Delete
    abstract suspend fun deleteTypeOfExpense(vararg categoryEntity: CategoryEntity)
}