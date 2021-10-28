package com.example.expensetrackerv2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.database.AppDatabase
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.models.TypeOfExpense
import com.example.expensetrackerv2.providers.SampleDataProvider

@Composable
fun MainExpensesInformation(expenses: List<Expense>, typeOfExpenseMap: Map<Int, TypeOfExpense>) {
    val moneyInWalletAmount = expenses.map { expense ->  expense.price * (typeOfExpenseMap[expense.typeOfExpenseId]?.type?.multiplier ?: 0) }.sum()

    Text("In Wallet: $moneyInWalletAmount", Modifier.padding(5.dp), style = MaterialTheme.typography.h4)
}

@Composable
fun MainComposable(navController: NavController) {
    val expenseDao = AppDatabase.getInstance(context = LocalContext.current).expenseDao()

    if(expenseDao.getAllTypesOfExpense().isEmpty() && expenseDao.getAllExpenses().isEmpty()) {
        SampleDataProvider.sampleTypeOfExpense(LocalContext.current)
        SampleDataProvider.sampleExpenses(LocalContext.current)
    }

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val expenses = expenseDao.getAllExpenses()
    val typeOfExpenseMap = expenseDao.getAllTypesOfExpenseAsMapWithIdKey()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { Text(text = "Placeholder") },
        bottomBar = {
            BottomAppBar(cutoutShape = CircleShape) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                }

                Spacer(Modifier.weight(1f, true))
                IconButton(onClick = { /* TODO */ }) {
                    Icon(Icons.Filled.Search, contentDescription = "Localized description")
                }
                IconButton(onClick = { /* TODO */ }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "Localized description")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Routes.ExpenseForm.route) }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    MainExpensesInformation(expenses, typeOfExpenseMap)
                    ExpensesList(expenses = expenses, typeOfExpenseMap = typeOfExpenseMap, navController = navController)
                }
            }
        }
    )
}