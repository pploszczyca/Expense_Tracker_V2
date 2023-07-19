package com.github.pploszczyca.expensetrackerv2.domain

data class ExpenseSummary(
    val yearlyExpenses: List<YearlyExpense>,
) {
    val totalIncome: Double by lazy {
        yearlyExpenses.sumOf { it.totalIncome }
    }

    val totalOutgo: Double by lazy {
        yearlyExpenses.sumOf { it.totalOutgo }
    }


    data class YearlyExpense(
        val year: Int,
        val monthlyExpenses: List<MonthlyExpense>,
    ) {
        val totalIncome: Double by lazy {
            monthlyExpenses.sumOf { it.totalIncome }
        }

        val totalOutgo: Double by lazy {
            monthlyExpenses.sumOf { it.totalOutgo }
        }


        data class MonthlyExpense(
            val month: Int,
            val dailyExpenses: List<DailyExpense>,
        ) {
            val totalIncome: Double by lazy {
                dailyExpenses.sumOf { it.totalIncome }
            }

            val totalOutgo: Double by lazy {
                dailyExpenses.sumOf { it.totalOutgo }
            }

            data class DailyExpense(
                val day: Int,
                val expenses: List<Expense>,
            ) {
                val totalIncome: Double by lazy {
                    expenses.totalIncome
                }

                val totalOutgo: Double by lazy {
                    expenses.totalOutgo
                }
            }
        }
    }
}
