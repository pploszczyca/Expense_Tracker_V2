package com.example.expensetrackerv2.ui.type_of_expense_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.database.models.TypeOfExpense
import com.example.expensetrackerv2.ui.bar.TopAppBarWithBack

@Composable
fun TypeOfExpenseSettings(
    navController: NavController,
    modelView: TypeOfExpenseSettingsModelView
) {
    val typeOfExpenseList by modelView.typesOfExpense.collectAsState(initial = emptyList())

    val isDialogFormVisible by modelView.isDialogFormVisible
    val isDeleteDialogFormVisible by modelView.isDeleteDialogFormVisible

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(id = R.string.drawer_type_of_expense_settings),
                navController = navController
            )
        },

        floatingActionButton = {
            FloatingActionButton(onClick = { modelView.onEvent(TypeOfExpenseSettingsEvent.OpenFormDialog()) }) {
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
                            modelView.onEvent(
                                TypeOfExpenseSettingsEvent.OpenFormDialog(
                                    it
                                )
                            )
                        },
                        onDeleteButtonClick = {
                            modelView.onEvent(
                                TypeOfExpenseSettingsEvent.OpenDeleteDialog(
                                    it
                                )
                            )
                        })
                }
            }

            if (isDialogFormVisible) {
                TypeOfExpenseDialogForm(modelView = modelView)
            }

            if (isDeleteDialogFormVisible) {
                TypeOfExpenseDeleteDialog(modelView = modelView)
            }
        })
}