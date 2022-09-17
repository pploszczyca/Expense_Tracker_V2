package com.example.expensetrackerv2.ui.form

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.database.models.ExpenseConstants
import com.example.expensetrackerv2.database.models.Category
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.database.models.view_models.getTypeOfExpense
import com.example.expensetrackerv2.use_cases.expense.*
import com.example.expensetrackerv2.use_cases.category.GetCategory
import com.example.expensetrackerv2.utilities.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditFormViewModel @Inject constructor(
    private val getExpenseWithCategory: GetExpenseWithCategory,
    private val insertExpenseWithCategory: InsertExpenseWithCategory,
    private val updateExpenseWithCategory: UpdateExpenseWithCategory,
    private val getExpensesTitles: GetExpensesTitles,
    private val getExpensesPlaces: GetExpensesPlaces,
    private val getCategory: GetCategory
) : ViewModel() {
    private val _id = mutableStateOf(ExpenseConstants.NEW_EXPENSE_ID)
    val id: State<Int> = _id

    private val _title = mutableStateOf("")
    val title: State<String> = _title

    private val _price = mutableStateOf("")
    val price: State<String> = _price

    private val _category = mutableStateOf(Category(id = -1))
    val category: State<Category> = _category

    private val _date = mutableStateOf(Date())
    val date: State<Date> = _date

    private val _place = mutableStateOf("")
    val place: State<String> = _place

    private val _description = mutableStateOf("")
    val description: State<String> = _description

    val expensesTitles: Flow<List<String>> = getExpensesTitles()
    val expensesPlaces: Flow<List<String>> = getExpensesPlaces()
    val typesOfExpense: Flow<List<Category>> = getCategory()

    private val isNewExpense get() = id.value == ExpenseConstants.NEW_EXPENSE_ID
    val isFormProper get() = _title.value.isNotEmpty() && _price.value.isNotEmpty() && _price.value.toDouble() >= 0.0 && _category.value.id != -1

    val submitButtonTextId = when (isNewExpense) {
        true -> R.string.add
        false -> R.string.update
    }

    private fun changeFormStates(expenseWithCategory: ExpenseWithCategory) {
        listOf(
            AddEditFormEvent.TitleChange(expenseWithCategory.title),
            AddEditFormEvent.PriceChange(if (expenseWithCategory.price != 0.0) expenseWithCategory.price.toString() else ""),
            AddEditFormEvent.DateChange(DateUtils.toOnlyDateString(expenseWithCategory.date)),
            AddEditFormEvent.PlaceChange(expenseWithCategory.place),
            AddEditFormEvent.DescriptionChange(expenseWithCategory.description),
            AddEditFormEvent.TypeOfAddEditChange(expenseWithCategory.getTypeOfExpense())
        ).forEach { event -> onEvent(event) }
    }

    private fun loadExpenseWithItsType(expenseID: Int) {
        viewModelScope.launch {
            getExpenseWithCategory(expenseID).collect {
                val expenseWithCategory = it ?: ExpenseWithCategory()
                changeFormStates(expenseWithCategory)
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
            is AddEditFormEvent.TypeOfAddEditChange -> _category.value = event.value
        }
    }

    private fun makeNewExpenseWithItsType() = ExpenseWithCategory(
        id = id.value,
        title = title.value,
        date = date.value,
        price = price.value.toDouble(),
        place = place.value,
        description = description.value,
        type = category.value.type,
        typeID = category.value.id,
        typeName = category.value.name
    )

    private fun insertOrUpdateNewExpense(newExpenseWithCategory: ExpenseWithCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isNewExpense) {
                insertExpenseWithCategory(newExpenseWithCategory)
            } else {
                updateExpenseWithCategory(newExpenseWithCategory)
            }
        }
    }

    fun formSubmit() {
        insertOrUpdateNewExpense(makeNewExpenseWithItsType())
    }
}