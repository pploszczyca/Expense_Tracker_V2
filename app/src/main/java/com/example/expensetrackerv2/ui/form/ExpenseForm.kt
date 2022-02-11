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
import com.example.expensetrackerv2.database.models.ExpenseConstants
import com.example.expensetrackerv2.database.models.TypeOfExpense
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import com.example.expensetrackerv2.database.repositories.TypeOfExpenseRepository
import com.example.expensetrackerv2.ui.bar.TopAppBarWithBack
import com.example.expensetrackerv2.utilities.DateUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun ExpenseForm(
    navController: NavController?,
    expenseWithItsTypeRepository: ExpenseWithItsTypeRepository,
    typeOfExpenseRepository: TypeOfExpenseRepository,
    expenseID: Int? = ExpenseConstants.NEW_EXPENSE_ID
) {
//    val expenseWithItsType =
//        if (expenseID == ExpenseConstants.NEW_EXPENSE_ID) ExpenseWithItsType() else expenseWithItsTypeRepository.getExpense(
//            expenseID!!
//        )
    val expenseWithItsType = if (expenseID == ExpenseConstants.NEW_EXPENSE_ID) expenseWithItsTypeRepository.getExpense(expenseID).observeAsState(ExpenseWithItsType()).value else ExpenseWithItsType()
    val expensesWithItsTypeList by expenseWithItsTypeRepository.getExpenses()
        .observeAsState(listOf())
    val typeOfExpenseList by typeOfExpenseRepository.getAllTypeOfExpenses().observeAsState(
        listOf(
            TypeOfExpense()
        )
    )
    val dataIsIncorrectString = stringResource(id = R.string.expense_form_data_incorrect)

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var title by remember { mutableStateOf(expenseWithItsType.title) }
    var price by remember { mutableStateOf(if (expenseWithItsType.price == 0.0) "" else expenseWithItsType.price.toString()) }
    var typeOfExpense by remember {
        mutableStateOf(
            TypeOfExpense(
                id = expenseWithItsType.typeID,
                name = expenseWithItsType.typeName,
                type = expenseWithItsType.type
            )
        )
    }
    var date by remember { mutableStateOf(DateUtils.toOnlyDateString(expenseWithItsType.date)) }
    var place by remember { mutableStateOf(expenseWithItsType.place) }
    var description by remember { mutableStateOf(expenseWithItsType.description) }

    val checkForm =
        title.isNotEmpty() && price.isNotEmpty() && price.toDouble() >= 0.0 && typeOfExpenseList.contains(
            typeOfExpense
        )

    Scaffold(topBar = {
        TopAppBarWithBack(
            title = stringResource(id = R.string.expense_form),
            navController = navController!!
        )
    }, content = {
        Column {
            AutoCompleteOutlinedTextField(
                value = title,
                onValueChange = { title = it },
                icon = Icons.Default.Title,
                label = stringResource(id = R.string.expense_form_title),
                suggestionsInput = expensesWithItsTypeList.map { it.title }
            )

            ExpenseFormTextField(
                value = price,
                onValueChange = { price = it },
                icon = Icons.Default.AttachMoney,
                label = stringResource(id = R.string.expense_form_price),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            CalendarDialogField(
                date = date,
                label = stringResource(id = R.string.expense_form_date)
            ) { dateFromDialog ->
                date = dateFromDialog.toString()
            }

            AutoCompleteOutlinedTextField(
                value = place,
                onValueChange = { place = it },
                icon = Icons.Default.Place,
                label = stringResource(id = R.string.expense_form_place),
                suggestionsInput = expensesWithItsTypeList.map { it.place }
            )

            ExpenseFormTextField(
                value = description,
                onValueChange = { description = it },
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
                            typeOfExpense = expenseType
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
                    navController!!.navigateUp()
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = dataIsIncorrectString)
                    }
                }

            }, modifier = Modifier.padding(20.dp)) {
                Text(
                    text = if (expenseWithItsType.id == ExpenseConstants.NEW_EXPENSE_ID) stringResource(
                        id = R.string.add
                    ) else stringResource(
                        id = R.string.update
                    )
                )
            }
        }
    })

}
