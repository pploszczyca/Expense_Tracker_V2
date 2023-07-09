package com.github.pploszczyca.expensetrackerv2.domain

data class ExpenseSummary(
    val yearlyExpenses: List<YearlyExpense>
) {
    data class YearlyExpense(
        val year: Int,
        val monthlyExpenses: List<MonthlyExpense>
    ) {
        data class MonthlyExpense(
            val month: Int,
            val dailyExpenses: List<DailyExpense>
        ) {
            data class DailyExpense(
                val day: Int,
                val expenses: List<Expense>
            )
        }
    }
}
