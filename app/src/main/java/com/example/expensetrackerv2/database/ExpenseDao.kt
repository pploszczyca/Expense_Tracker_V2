package com.example.expensetrackerv2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.models.TypeOfExpense

@Dao
abstract class ExpenseDao {
    @Query("SELECT * FROM expense")
    abstract fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM typeofexpense")
    abstract fun getAllTypesOfExpense(): List<TypeOfExpense>

    fun getAllTypesOfExpenseAsMap(): Map<Int, TypeOfExpense> = getAllTypesOfExpense().map { it.id to it }.toMap()

    @Insert
    abstract fun insertAllExpenses(vararg expenses: Expense)

    @Insert
    abstract fun insertAllTypesOfExpense(vararg expenses: TypeOfExpense)
}