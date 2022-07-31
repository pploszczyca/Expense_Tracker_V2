package com.example.expensetrackerv2.ui.form

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.database.models.ExpenseConstants
import com.example.expensetrackerv2.database.models.TypeOfExpense
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.models.view_models.getTypeOfExpense
import com.example.expensetrackerv2.use_cases.expense.*
import com.example.expensetrackerv2.use_cases.type_of_expense.GetTypesOfExpense
import com.example.expensetrackerv2.utilities.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditFormViewModel @Inject constructor(
    private val getExpenseWithItsType: GetExpenseWithItsType,
    private val insertExpenseWithItsType: InsertExpenseWithItsType,
    private val updateExpenseWithItsType: UpdateExpenseWithItsType,
    private val getExpensesTitles: GetExpensesTitles,
    private val getExpensesPlaces: GetExpensesPlaces,
    private val getTypesOfExpense: GetTypesOfExpense
) : ViewModel() {
    private val _id = mutableStateOf(ExpenseConstants.NEW_EXPENSE_ID)
    val id: State<Int> = _id

    private val _title = mutableStateOf("")
    val title: State<String> = _title

    private val _price = mutableStateOf("")
    val price: State<String> = _price

    private val _typeOfExpense = mutableStateOf(TypeOfExpense(id = -1))
    val typeOfExpense: State<TypeOfExpense> = _typeOfExpense

    private val _date = mutableStateOf(Date())
    val date: State<Date> = _date

    private val _place = mutableStateOf("")
    val place: State<String> = _place

    private val _description = mutableStateOf("")
    val description: State<String> = _description

    val expensesTitles: Flow<List<String>> = getExpensesTitles()
    val expensesPlaces: Flow<List<String>> = getExpensesPlaces()
    val typesOfExpense: Flow<List<TypeOfExpense>> = getTypesOfExpense()

    private val isNewExpense get() = id.value == ExpenseConstants.NEW_EXPENSE_ID
    val isFormProper get() = _title.value.isNotEmpty() && _price.value.isNotEmpty() && _price.value.toDouble() >= 0.0 && _typeOfExpense.value.id != -1

    val submitButtonTextId = when (isNewExpense) {
        true -> R.string.add
        false -> R.string.update
    }

    private fun changeFormStates(expenseWithItsType: ExpenseWithItsType) {
        listOf(
            AddEditFormEvent.TitleChange(expenseWithItsType.title),
            AddEditFormEvent.PriceChange(if (expenseWithItsType.price != 0.0) expenseWithItsType.price.toString() else ""),
            AddEditFormEvent.DateChange(DateUtils.toOnlyDateString(expenseWithItsType.date)),
            AddEditFormEvent.PlaceChange(expenseWithItsType.place),
            AddEditFormEvent.DescriptionChange(expenseWithItsType.description),
            AddEditFormEvent.TypeOfAddEditChange(expenseWithItsType.getTypeOfExpense())
        ).forEach { event -> onEvent(event) }
    }

    private fun loadExpenseWithItsType(expenseID: Int) {
        viewModelScope.launch {
            getExpenseWithItsType(expenseID).collect {
                val expenseWithItsType = it ?: ExpenseWithItsType()
                changeFormStates(expenseWithItsType)
            }
        }
    }

    fun onEvent(event: AddEditFormEvent) {
        when (event) {
            is AddEditFormEvent.IdChange -> {
                _id.value = event.value
                loadExpenseWithItsType(id.value)
            }
            is AddEditFormEvent.TitleChange -> _title.value = event.value
            is AddEditFormEvent.PriceChange -> _price.value = event.value
            is AddEditFormEvent.DateChange -> _date.value = DateUtils.stringToDate(event.value)
            is AddEditFormEvent.PlaceChange -> _place.value = event.value
            is AddEditFormEvent.DescriptionChange -> _description.value = event.value
            is AddEditFormEvent.TypeOfAddEditChange -> _typeOfExpense.value = event.value
        }
    }

    private fun makeNewExpenseWithItsType() = ExpenseWithItsType(
        id = id.value,
        title = title.value,
        date = date.value,
        price = price.value.toDouble(),
        place = place.value,
        description = description.value,
        type = typeOfExpense.value.type,
        typeID = typeOfExpense.value.id,
        typeName = typeOfExpense.value.name
    )

    private fun insertOrUpdateNewExpense(newExpenseWithItsType: ExpenseWithItsType) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isNewExpense) {
                insertExpenseWithItsType(newExpenseWithItsType)
            } else {
                updateExpenseWithItsType(newExpenseWithItsType)
            }
        }
    }

    fun formSubmit() {
        insertOrUpdateNewExpense(makeNewExpenseWithItsType())
    }
}