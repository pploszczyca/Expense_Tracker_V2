package com.example.expensetrackerv2.ui.form

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Title
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.expensetrackerv2.utilities.DateUtils
import kotlinx.coroutines.launch

@Composable
fun AddEditForm(
    navController: NavController,
    viewModel: AddEditFormViewModel
) {
    val typeOfExpenseList by viewModel.typesOfExpense.collectAsState(initial = emptyList())
    val titlesList by viewModel.expensesTitles.observeAsState(emptyList())
    val placesList by viewModel.expensesPlaces.observeAsState(emptyList())

    val dataIsIncorrectString = stringResource(id = R.string.expense_form_data_incorrect)

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val title by viewModel.title
    val price by viewModel.price
    val typeOfExpense by viewModel.typeOfExpense
    val date by viewModel.date
    val place by viewModel.place
    val description by viewModel.description

    val checkForm =
        title.isNotEmpty() && price.isNotEmpty() && price.toDouble() >= 0.0 && typeOfExpenseList.contains(
            typeOfExpense
        )

    Scaffold(topBar = {
        TopAppBarWithBack(
            title = stringResource(id = R.string.expense_form),
            navController = navController
        )
    }, content = {
        Column {
            AutoCompleteOutlinedTextField(
                value = title,
                onValueChange = { viewModel.onEvent(AddEditFormEvent.TitleChange(it)) },
                icon = Icons.Default.Title,
                label = stringResource(id = R.string.expense_form_title),
                suggestionsInput = titlesList
            )

            AddEditFormTextField(
                value = price,
                onValueChange = { viewModel.onEvent(AddEditFormEvent.PriceChange(it)) },
                icon = Icons.Default.AttachMoney,
                label = stringResource(id = R.string.expense_form_price),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            CalendarDialogField(
                date = DateUtils.toOnlyDateString(date),
                label = stringResource(id = R.string.expense_form_date)
            ) { dateFromDialog ->
                viewModel.onEvent(AddEditFormEvent.DateChange(dateFromDialog.toString()))
            }

            AutoCompleteOutlinedTextField(
                value = place,
                onValueChange = { viewModel.onEvent(AddEditFormEvent.PlaceChange(it)) },
                icon = Icons.Default.Place,
                label = stringResource(id = R.string.expense_form_place),
                suggestionsInput = placesList
            )

            AddEditFormTextField(
                value = description,
                onValueChange = { viewModel.onEvent(AddEditFormEvent.DescriptionChange(it)) },
                icon = Icons.Default.Message,
                label = stringResource(id = R.string.expense_form_description),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )

            Column(
                modifier = Modifier
                    .selectableGroup()
            ) {
                typeOfExpenseList.forEach { expenseType ->
                    Row(verticalAlignment = CenterVertically) {
                        RadioButton(selected = typeOfExpense == expenseType, onClick = {
                            viewModel.onEvent(
                                AddEditFormEvent.TypeOfAddEditChange(
                                    expenseType
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
                if (checkForm) {
                    viewModel.formSubmit()
                    navController.navigateUp()
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = dataIsIncorrectString)
                    }
                }

            }, modifier = Modifier.padding(20.dp)) {
                Text(
                    text = if (viewModel.isNewExpense()) stringResource(
                        id = R.string.add
                    ) else stringResource(
                        id = R.string.update
                    )
                )
            }
        }
    })

}
