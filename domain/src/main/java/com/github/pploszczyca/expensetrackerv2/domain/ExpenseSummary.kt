package com.github.pploszczyca.expensetrackerv2.domain

data class ExpenseSummary(
    val yearlyExpenses: List<YearlyExpense>,
) {
    val totalIncome: Price = yearlyExpenses.sumOf { it.totalIncome }

    val totalOutgo: Price = yearlyExpenses.sumOf { it.totalOutgo }


    data class YearlyExpense(
        val year: Int,
        val monthlyExpenses: List<MonthlyExpense>,
    ) {
        val totalIncome: Price = monthlyExpenses.sumOf { it.totalIncome }

        val totalOutgo: Price = monthlyExpenses.sumOf { it.totalOutgo }


        data class MonthlyExpense(
            val month: Int,
            val dailyExpenses: List<DailyExpense>,
        ) {
            val totalIncome: Price = dailyExpenses.sumOf { it.totalIncome }

            val totalOutgo: Price = dailyExpenses.sumOf { it.totalOutgo }

            data class DailyExpense(
                val day: Int,
                val expenses: List<Expense>,
            ) {
                val totalIncome: Price = expenses.totalIncome

                val totalOutgo: Price = expenses.totalOutgo
            }
        }
    }
}
