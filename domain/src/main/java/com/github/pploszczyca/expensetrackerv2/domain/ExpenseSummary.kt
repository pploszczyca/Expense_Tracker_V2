package com.github.pploszczyca.expensetrackerv2.domain

data class ExpenseSummary(
    val yearlyExpenses: List<YearlyExpense>,
) {
    val totalIncome: Price get() = yearlyExpenses.sumOf { it.totalIncome }

    val totalOutgo: Price get() = yearlyExpenses.sumOf { it.totalOutgo }

    val total: Price get() = totalIncome + totalOutgo


    data class YearlyExpense(
        val year: Int,
        val monthlyExpenses: List<MonthlyExpense>,
    ) {
        val totalIncome: Price get() = monthlyExpenses.sumOf { it.totalIncome }

        val totalOutgo: Price get() = monthlyExpenses.sumOf { it.totalOutgo }


        data class MonthlyExpense(
            val month: Int,
            val dailyExpenses: List<DailyExpense>,
        ) {
            val totalIncome: Price get() = dailyExpenses.sumOf { it.totalIncome }

            val totalOutgo: Price get() = dailyExpenses.sumOf { it.totalOutgo }

            data class DailyExpense(
                val day: Int,
                val expenses: List<Expense>,
            ) {
                val totalIncome: Price get() = expenses.totalIncome

                val totalOutgo: Price get() = expenses.totalOutgo
            }
        }
    }
}
