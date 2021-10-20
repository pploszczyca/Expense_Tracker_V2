package com.example.expensetrackerv2

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.expensetrackerv2.models.Expense
import com.example.expensetrackerv2.ui.theme.ExpenseTrackerV2Theme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerv2.database.AppDatabase
import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.models.TypeOfExpense
import com.example.expensetrackerv2.providers.SampleDataProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerV2Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    NavHostComposable(applicationContext)
                }
            }
        }
    }
}

@Composable
fun NavHostComposable(context: Context?) {
    val navController = rememberNavController()
    val expenseDao = AppDatabase.getInstance(context = context).expenseDao()

    if(expenseDao.getAllTypesOfExpense().isEmpty() && expenseDao.getAllExpenses().isEmpty()) {
        SampleDataProvider.sampleTypeOfExpense(context)
        SampleDataProvider.sampleExpenses(context)
    }

    NavHost(navController = navController, startDestination = Routes.Main.route) {
        composable(Routes.Main.route) { MainComposable(navController = navController, expenseDao = expenseDao)}
        composable(Routes.ExpenseForm.route) { AddNewExpenseForm(navController = navController, context = context) }
    }
}

@Composable
fun MainComposable(navController: NavController, expenseDao: ExpenseDao) {
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
                ExpensesList(expenses = expenses, typeOfExpenseMap = typeOfExpenseMap, expenseDao)
            }
        }
    )
}

@Composable
fun ExpensesList(expenses: List<Expense>, typeOfExpenseMap: Map<Int, TypeOfExpense>, expenseDao: ExpenseDao) {
    LazyColumn(Modifier.padding(3.dp)) {
        items(expenses) { expense ->
            ExpenseCard(expense = expense, typeOfExpense = typeOfExpenseMap[expense.typeOfExpenseId], expenseDao)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExpenseTrackerV2Theme {
//        MainComposable(SampleDataProvider.sampleExpenses())
//        NavHostComposable()
    }
}