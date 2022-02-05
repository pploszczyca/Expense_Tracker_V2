package com.example.expensetrackerv2.ui.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.Routes
import com.example.expensetrackerv2.database.AppDatabase
import com.example.expensetrackerv2.database.models.ExpenseConstants
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeDatabaseRepository
import com.example.expensetrackerv2.utilities.DateUtils
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun ExpenseForm(navController: NavController?, expenseID: Int? = ExpenseConstants.NEW_EXPENSE_ID) {
    val currentContext = LocalContext.current
    val expenseDao = AppDatabase.getInstance(context = currentContext).expenseDao()
    val expenseWithItsTypeRepository = ExpenseWithItsTypeDatabaseRepository(expenseDao)
    val expenseWithItsType =
        if (expenseID == ExpenseConstants.NEW_EXPENSE_ID) ExpenseWithItsType() else expenseWithItsTypeRepository.getExpense(
            expenseID!!
        )
    val expenses = expenseDao.getAllExpenses()
    val typeOfExpenseList = expenseDao.getAllTypesOfExpense()
    val dataIsIncorrectString = stringResource(id = R.string.expense_form_data_incorrect)

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val dialogState = rememberMaterialDialogState()

    var title by remember { mutableStateOf(expenseWithItsType.title) }
    var price by remember { mutableStateOf(if (expenseWithItsType.price == 0.0) "" else expenseWithItsType.price.toString()) }
    var typeOfExpense by remember { mutableStateOf(typeOfExpenseList.first()) }
    var date by remember { mutableStateOf(DateUtils.toOnlyDateString(expenseWithItsType.date)) }
    var place by remember { mutableStateOf(expenseWithItsType.place) }
    var description by remember { mutableStateOf(expenseWithItsType.description) }

    val checkForm = title.isNotEmpty() && price.isNotEmpty() && price.toDouble() >= 0.0

    Column {
        AutoCompleteOutlinedTextField(
            value = title,
            onValueChange = { title = it },
            icon = Icons.Default.Title,
            label = stringResource(id = R.string.expense_form_title),
            suggestionsInput = expenses.map { it.title }
        )

        ExpenseFormTextField(
            value = price,
            onValueChange = { price = it },
            icon = Icons.Default.AttachMoney,
            label = stringResource(id = R.string.expense_form_price),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        ExpenseFormTextField(
            value = date,
            onValueChange = { date = it },
            icon = Icons.Default.Today,
            label = stringResource(id = R.string.expense_form_date),
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clickable { dialogState.show() }
        )

        AutoCompleteOutlinedTextField(
            value = place,
            onValueChange = { place = it },
            icon = Icons.Default.Place,
            label = stringResource(id = R.string.expense_form_place),
            suggestionsInput = expenses.map { it.place }
        )

        ExpenseFormTextField(
            value = description,
            onValueChange = { description = it },
            icon = Icons.Default.Message,
            label = stringResource(id = R.string.expense_form_description),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )

        Row(
            modifier = Modifier
                .selectableGroup(),
            verticalAlignment = CenterVertically
        ) {
            typeOfExpenseList.forEach { expenseType ->
                RadioButton(selected = typeOfExpense == expenseType, onClick = {
                    typeOfExpense = expenseType
                })
                Text(text = expenseType.name, style = MaterialTheme.typography.subtitle1)
            }
        }
    }

    MaterialDialog(dialogState = dialogState, buttons = {
        positiveButton(stringResource(id = R.string.ok))
        negativeButton(stringResource(id = R.string.cancel))
    }) {
        datepicker { dateFromDialog ->
            date = dateFromDialog.toString()
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
                runBlocking {
                    val newExpenseWithItsType = ExpenseWithItsType(
                        id = expenseWithItsType.id,
                        title = title,
                        date = DateUtils.stringToDate(date),
                        price = price.toDouble(),
                        place = place,
                        description = description,
                        type = typeOfExpense.type,
                        typeID = typeOfExpense.id,
                        typeName = typeOfExpense.name
                    )

                    if (newExpenseWithItsType.id == ExpenseConstants.NEW_EXPENSE_ID) expenseWithItsTypeRepository.insertExpense(
                        newExpenseWithItsType
                    ) else expenseWithItsTypeRepository.updateExpense(
                        newExpenseWithItsType
                    )
                }
                navController!!.popBackStack()
                navController.navigate(Routes.Main.route)
            } else {
                scope.launch {
                    snackbarHostState.showSnackbar(message = dataIsIncorrectString)
                }
            }

        }, modifier = Modifier.padding(20.dp)) {
            Text(
                text = if (expenseWithItsType.id == ExpenseConstants.NEW_EXPENSE_ID) stringResource(
                    id = R.string.expense_form_add
                ) else stringResource(
                    id = R.string.expense_form_update
                )
            )
        }
    }
}
