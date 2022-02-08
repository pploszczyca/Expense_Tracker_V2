package com.example.expensetrackerv2.ui.type_of_expense_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.database.models.TypeOfExpense
import com.example.expensetrackerv2.database.models.TypeOfExpenseConstants
import com.example.expensetrackerv2.database.repositories.TypeOfExpenseRepository
import com.example.expensetrackerv2.ui.bar.TopAppBarWithBack
import kotlinx.coroutines.runBlocking

@Composable
fun TypeOfExpenseSettings(
    navController: NavController,
    typeOfExpenseRepository: TypeOfExpenseRepository
) {
    val typeOfExpenseList by typeOfExpenseRepository.getAllTypeOfExpenses().observeAsState(listOf())

    var typeOfExpense by remember {
        mutableStateOf(TypeOfExpense())
    }
    val isThisNewTypeOfExpense = typeOfExpense.id == TypeOfExpenseConstants.NEW_TYPE_OF_EXPENSE_ID
    val confirmButtonTitle =
        stringResource(id = if (isThisNewTypeOfExpense) R.string.add else R.string.update)
    var isDialogFormVisible by remember {
        mutableStateOf(false)
    }
    var isDeleteDialogFormVisible by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(id = R.string.drawer_type_of_expense_settings),
                navController = navController
            )
        },

        floatingActionButton = {
            FloatingActionButton(onClick = { isDialogFormVisible = true }) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.add_icon))
            }
        },

        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(typeOfExpenseList) { typeOfExpenseItem: TypeOfExpense ->
                    TypeOfExpenseCard(
                        typeOfExpense = typeOfExpenseItem,
                        onUpdateButtonClick = {
                            typeOfExpense = it
                            isDialogFormVisible = true
                        },
                        onDeleteButtonClick = {
                            typeOfExpense = it
                            isDeleteDialogFormVisible = true
                        })
                }
            }

            if (isDialogFormVisible) {
                TypeOfExpenseDialogForm(
                    typeOfExpense = typeOfExpense,
                    confirmButtonTitle = confirmButtonTitle,
                    onConfirmButtonClick = {
                        runBlocking {
                            if (isThisNewTypeOfExpense) {
                                typeOfExpenseRepository.insertTypeOfExpense(it)
                            } else {
                                typeOfExpenseRepository.updateTypeOfExpense(it)
                            }
                            typeOfExpense = TypeOfExpense()
                        }
                        isDialogFormVisible = false
                    },
                    onDismissButtonClick = {
                        typeOfExpense = TypeOfExpense()
                        isDialogFormVisible = false
                    }
                )
            }

            if (isDeleteDialogFormVisible) {
                TypeOfExpenseDeleteDialog(
                    typeOfExpense = typeOfExpense,
                    onConfirmButtonClick = {
                        runBlocking {
                            typeOfExpenseRepository.deleteTypeOfExpense(it)
                        }
                        isDeleteDialogFormVisible = false
                        typeOfExpense = TypeOfExpense()
                    },
                    onDismissButtonClick = {
                        isDeleteDialogFormVisible = false
                        typeOfExpense = TypeOfExpense()
                    })
            }
        })
}