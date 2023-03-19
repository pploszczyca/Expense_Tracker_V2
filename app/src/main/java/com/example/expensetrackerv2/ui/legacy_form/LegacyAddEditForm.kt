package com.example.expensetrackerv2.ui.legacy_form

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Title
import androidx.compose.runtime.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditForm(
    navController: NavController,
    viewModel: LegacyAddEditFormViewModel
) {
    val title by viewModel.title
    val price by viewModel.price
    val typeOfExpense by viewModel.categoryEntity
    val date by viewModel.date
    val place by viewModel.place
    val description by viewModel.description
    val typeOfExpenseList by viewModel.typesOfExpense.collectAsState(initial = emptyList())
    val titleSuggestions by viewModel.expensesTitles.collectAsState(emptyList())
    val placeSuggestions by viewModel.expensesPlaces.collectAsState(emptyList())

    val dataIsIncorrectString = stringResource(id = R.string.expense_form_data_incorrect)
    val submitButtonText = stringResource(id = viewModel.submitButtonTextId)

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(topBar = {
        TopAppBarWithBack(
            title = stringResource(id = R.string.expense_form),
            navController = navController
        )
    }, content = {
        Column(modifier = Modifier.padding(it)) {
            AutoCompleteOutlinedTextField(
                value = title,
                onValueChange = { viewModel.onEvent(LegacyAddEditFormEvent.TitleChange(it)) },
                icon = Icons.Default.Title,
                label = stringResource(id = R.string.expense_form_title),
                suggestionsInput = titleSuggestions
            )

            AddEditFormTextField(
                value = price,
                onValueChange = { viewModel.onEvent(LegacyAddEditFormEvent.PriceChange(it)) },
                icon = Icons.Default.AttachMoney,
                label = stringResource(id = R.string.expense_form_price),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            CalendarDialogField(
                date = DateUtils.toOnlyDateString(date),
                label = stringResource(id = R.string.expense_form_date)
            ) { dateFromDialog ->
                viewModel.onEvent(LegacyAddEditFormEvent.DateChange(dateFromDialog.toString()))
            }

            AutoCompleteOutlinedTextField(
                value = place,
                onValueChange = { viewModel.onEvent(LegacyAddEditFormEvent.PlaceChange(it)) },
                icon = Icons.Default.Place,
                label = stringResource(id = R.string.expense_form_place),
                suggestionsInput = placeSuggestions
            )

            AddEditFormTextField(
                value = description,
                onValueChange = { viewModel.onEvent(LegacyAddEditFormEvent.DescriptionChange(it)) },
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
                                LegacyAddEditFormEvent.TypeOfAddEditChange(
                                    expenseType
                                )
                            )
                        })
                        Text(text = expenseType.name, style = MaterialTheme.typography.bodyMedium)
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
                if (viewModel.isFormProper) {
                    viewModel.formSubmit()
                    navController.navigateUp()
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = dataIsIncorrectString)
                    }
                }

            }, modifier = Modifier.padding(20.dp)) {
                Text(text = submitButtonText)
            }
        }
    })

}
