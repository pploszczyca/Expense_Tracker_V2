package com.github.pploszczyca.expensetrackerv2.domain

data class ExpenseSummary(
    val yearlyExpenses: List<YearlyExpense>,
) {
    val totalIncome: Double get() =
        yearlyExpenses.sumOf { it.totalIncome }

    val totalOutgo: Double get() =
        yearlyExpenses.sumOf { it.totalOutgo }


    data class YearlyExpense(
        val year: Int,
        val monthlyExpenses: List<MonthlyExpense>,
    ) {
        val totalIncome: Double get() =
            monthlyExpenses.sumOf { it.totalIncome }

        val totalOutgo: Double get() =
            monthlyExpenses.sumOf { it.totalOutgo }


        data class MonthlyExpense(
            val month: Int,
            val dailyExpenses: List<DailyExpense>,
        ) {
            val totalIncome: Double get() =
                dailyExpenses.sumOf { it.totalIncome }

            val totalOutgo: Double get() =
                dailyExpenses.sumOf { it.totalOutgo }

            data class DailyExpense(
                val day: Int,
                val expenses: List<Expense>,
            ) {
                val totalIncome: Double get() =
                    expenses.filter { it.category.type == Category.Type.INCOME }.sumOf { it.price }

                val totalOutgo: Double get() =
                    expenses.filter { it.category.type == Category.Type.OUTGO }.sumOf { it.price }
            }
        }
    }
}
