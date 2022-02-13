package com.example.expensetrackerv2.database

import androidx.room.*
import com.example.expensetrackerv2.database.models.Expense
import com.example.expensetrackerv2.database.models.TypeOfExpense
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ExpenseDao {
    //QUERIES
    @Query("SELECT * FROM expense ORDER BY date DESC")
    abstract fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM typeofexpense")
    abstract fun getAllTypesOfExpense(): Flow<List<TypeOfExpense>>

    @Query("SELECT * FROM ExpenseWithItsType ORDER BY date DESC")
    abstract fun getAllExpenseWithItsType(): Flow<List<ExpenseWithItsType>>

    @Query("SELECT * FROM expense WHERE id = :expenseID")
    abstract fun getExpense(expenseID: Int): Expense

    @Query("SELECT * FROM ExpenseWithItsType WHERE id = :expenseID")
    abstract fun getExpenseWithItsType(expenseID: Int): Flow<ExpenseWithItsType?>

    // INSERTS
    @Insert
    abstract suspend fun insertAllExpenses(vararg expenses: Expense)

    suspend fun insertAllExpenses(expenses: List<Expense>) =
        expenses.forEach { insertAllExpenses(it) }

    @Insert
    abstract suspend fun insertAllTypesOfExpense(vararg expenses: TypeOfExpense)

    // UPDATE
    @Update
    abstract suspend fun updateExpense(expense: Expense)

    @Update
    abstract suspend fun updateTypeOfExpense(typeOfExpense: TypeOfExpense)

    // DELETES
    @Query("DELETE FROM Expense WHERE id = :id")
    abstract suspend fun deleteExpenseByID(id: Int)

    @Query("DELETE FROM EXPENSE")
    abstract suspend fun deleteAllExpenses()

    @Delete
    abstract suspend fun deleteExpense(expense: Expense)

    suspend fun deleteExpense(expenseWithItsType: ExpenseWithItsType) =
        deleteExpenseByID(expenseWithItsType.id)

    @Delete
    abstract suspend fun deleteTypeOfExpense(vararg typeOfExpense: TypeOfExpense)
}