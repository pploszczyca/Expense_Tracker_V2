package com.example.expensetrackerv2.database

import androidx.room.*
import com.example.expensetrackerv2.database.models.Expense
import com.example.expensetrackerv2.database.models.TypeOfExpense
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType

@Dao
abstract class ExpenseDao {
    //QUERIES
    @Query("SELECT * FROM expense ORDER BY date DESC")
    abstract fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM typeofexpense")
    abstract fun getAllTypesOfExpense(): List<TypeOfExpense>

    @Query("SELECT * FROM ExpenseWithItsType ORDER BY date DESC")
    abstract fun getAllExpenseWithItsType(): List<ExpenseWithItsType>

    @Query("SELECT * FROM expense WHERE id = :expenseID")
    abstract fun getExpense(expenseID: Int): Expense

    @Query("SELECT * FROM ExpenseWithItsType WHERE id = :expenseID")
    abstract fun getExpenseWithItsType(expenseID: Int): ExpenseWithItsType

    // INSERTS
    @Insert
    abstract suspend fun insertAllExpenses(vararg expenses: Expense)

    @Insert
    abstract suspend fun insertAllTypesOfExpense(vararg expenses: TypeOfExpense)

    // UPDATE
    @Update
    abstract suspend fun updateExpense(expense: Expense)

    // DELETES
    @Query("DELETE FROM Expense WHERE id = :id")
    abstract suspend fun deleteExpenseByID(id: Int)

    @Delete
    abstract suspend fun deleteExpense(expense: Expense)

    suspend fun deleteExpense(expenseWithItsType: ExpenseWithItsType) = deleteExpenseByID(expenseWithItsType.id)


}