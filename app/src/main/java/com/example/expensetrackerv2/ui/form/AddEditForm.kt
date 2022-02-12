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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.expensetrackerv2.utilities.DateUtils
import kotlinx.coroutines.launch

@Composable
fun AddEditForm(
    navController: NavController,
    addEditFormViewModel: AddEditFormViewModel
) {
    val typeOfExpenseList by addEditFormViewModel.typesOfExpense.observeAsState(
        emptyList()
    )
    val titlesList by addEditFormViewModel.expensesTitles.observeAsState(emptyList())
    val placesList by addEditFormViewModel.expensesPlaces.observeAsState(emptyList())

    val dataIsIncorrectString = stringResource(id = R.string.expense_form_data_incorrect)

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val title by addEditFormViewModel.title
    val price by addEditFormViewModel.price
    val typeOfExpense by addEditFormViewModel.typeOfExpense
    val date by addEditFormViewModel.date
    val place by addEditFormViewModel.place
    val description by addEditFormViewModel.description

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
                onValueChange = { addEditFormViewModel.onEvent(AddEditFormEvent.TitleChange(it)) },
                icon = Icons.Default.Title,
                label = stringResource(id = R.string.expense_form_title),
                suggestionsInput = titlesList
            )

            AddEditFormTextField(
                value = price,
                onValueChange = { addEditFormViewModel.onEvent(AddEditFormEvent.PriceChange(it)) },
                icon = Icons.Default.AttachMoney,
                label = stringResource(id = R.string.expense_form_price),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            CalendarDialogField(
                date = DateUtils.toOnlyDateString(date),
                label = stringResource(id = R.string.expense_form_date)
            ) { dateFromDialog ->
                addEditFormViewModel.onEvent(AddEditFormEvent.DateChange(dateFromDialog.toString()))
            }

            AutoCompleteOutlinedTextField(
                value = place,
                onValueChange = { addEditFormViewModel.onEvent(AddEditFormEvent.PlaceChange(it)) },
                icon = Icons.Default.Place,
                label = stringResource(id = R.string.expense_form_place),
                suggestionsInput = placesList
            )

            AddEditFormTextField(
                value = description,
                onValueChange = { addEditFormViewModel.onEvent(AddEditFormEvent.DescriptionChange(it)) },
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
                            addEditFormViewModel.onEvent(
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
                    addEditFormViewModel.formSubmit()
                    navController.navigateUp()
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = dataIsIncorrectString)
                    }
                }

            }, modifier = Modifier.padding(20.dp)) {
                Text(
                    text = if (addEditFormViewModel.isNewExpense()) stringResource(
                        id = R.string.add
                    ) else stringResource(
                        id = R.string.update
                    )
                )
            }
        }
    })

}
