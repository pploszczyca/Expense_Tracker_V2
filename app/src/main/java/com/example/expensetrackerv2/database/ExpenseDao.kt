package com.example.expensetrackerv2.database

import androidx.room.*
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.models.TypeOfExpense

@Dao
abstract class ExpenseDao {
    //QUERIES
    @Query("SELECT * FROM expense ORDER BY date DESC")
    abstract fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM typeofexpense")
    abstract fun getAllTypesOfExpense(): List<TypeOfExpense>

    fun getAllTypesOfExpenseAsMapWithIdKey(): Map<Int, TypeOfExpense> = getAllTypesOfExpense().map { it.id to it }.toMap()

    @Query("SELECT * FROM expense WHERE id = :expenseID")
    abstract fun getExpense(expenseID: Int): Expense

    // INSERTS
    @Insert
    abstract suspend fun insertAllExpenses(vararg expenses: Expense)

    @Insert
    abstract suspend fun insertAllTypesOfExpense(vararg expenses: TypeOfExpense)

    // UPDATE
    @Update
    abstract suspend fun updateExpense(expense: Expense)

    // DELETES
    @Delete
    abstract suspend fun deleteExpense(expense: Expense)
}