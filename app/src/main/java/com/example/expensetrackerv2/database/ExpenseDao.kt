package com.example.expensetrackerv2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.models.TypeOfExpense

@Dao
abstract class ExpenseDao {
    //QUERIES
    @Query("SELECT * FROM expense")
    abstract fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM typeofexpense")
    abstract fun getAllTypesOfExpense(): List<TypeOfExpense>

    fun getAllTypesOfExpenseAsMapWithIdKey(): Map<Int, TypeOfExpense> = getAllTypesOfExpense().map { it.id to it }.toMap()

    fun getAllTypesOfExpenseAsMapWithNameKey(): Map<String, TypeOfExpense> = getAllTypesOfExpense().map { it.name to it }.toMap()

    // INSERTS
    @Insert
    abstract fun insertAllExpenses(vararg expenses: Expense)

    @Insert
    abstract fun insertAllTypesOfExpense(vararg expenses: TypeOfExpense)

    // DELETES
    @Delete
    abstract fun deleteExpense(expense: Expense)
}