package com.example.expensetrackerv2.ui.form.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Title
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.ui.bar.TopAppBarWithBack
import com.example.expensetrackerv2.ui.form.viewModel.AddEditFormViewModel
import com.example.expensetrackerv2.ui.form.viewModel.AddEditFormViewModelImpl
import com.example.expensetrackerv2.utilities.DateUtils
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddEditForm(
    navController: NavController,
    viewModel: AddEditFormViewModel,
) {
    val viewState = viewModel.viewState

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val dataIsIncorrectString = stringResource(id = R.string.expense_form_data_incorrect)

    Scaffold(topBar = {
        TopAppBarWithBack(
            title = stringResource(id = R.string.expense_form),
            navController = navController
        )
    }, content = {
        Column {
            AutoCompleteOutlinedTextField(
                value = viewState.title,
                onValueChange = { viewModel.onEvent(AddEditFormViewModel.Event.TitleChange(it)) },
                icon = Icons.Default.Title,
                label = stringResource(id = R.string.expense_form_title),
                suggestionsInput = viewState.titleSuggestions
            )

            AddEditFormTextField(
                value = viewState.price,
                onValueChange = { viewModel.onEvent(AddEditFormViewModel.Event.PriceChange(it)) },
                icon = Icons.Default.AttachMoney,
                label = stringResource(id = R.string.expense_form_price),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            CalendarDialogField(
                date = DateUtils.toOnlyDateString(viewState.date),
                label = stringResource(id = R.string.expense_form_date)
            ) { dateFromDialog ->
                viewModel.onEvent(AddEditFormViewModel.Event.DateChange(dateFromDialog.toString()))
            }

            AutoCompleteOutlinedTextField(
                value = viewState.place,
                onValueChange = { viewModel.onEvent(AddEditFormViewModel.Event.PlaceChange(it)) },
                icon = Icons.Default.Place,
                label = stringResource(id = R.string.expense_form_place),
                suggestionsInput = viewState.placeSuggestions
            )

            AddEditFormTextField(
                value = viewState.description,
                onValueChange = { viewModel.onEvent(AddEditFormViewModel.Event.DescriptionChange(it)) },
                icon = Icons.Default.Message,
                label = stringResource(id = R.string.expense_form_description),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )

            Column(
                modifier = Modifier
                    .selectableGroup()
            ) {
                viewState.typeOfExpenseRecords.forEach { expenseType ->
                    Row(verticalAlignment = CenterVertically) {
                        RadioButton(selected = expenseType.selected, onClick = {
                            viewModel.onEvent(
                                AddEditFormViewModel.Event.TypeOfAddEditChange(
                                    expenseType.id
                                )
                            )
                        })
                        Text(text = expenseType.name, style = MaterialTheme.typography.subtitle1)
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize(), Alignment.BottomEnd) {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 56.dp)
            )

            Button(onClick = {
                if (viewState.isAllDataLoaded) {
                    viewModel.onEvent(AddEditFormViewModel.Event.FormSubmit)
                    navController.navigateUp()
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = dataIsIncorrectString)
                    }
                }

            }, modifier = Modifier.padding(20.dp)) {
                Text(
                    text = stringResource(id = viewState.buttonTextId)
                )
            }
        }
    })

}
