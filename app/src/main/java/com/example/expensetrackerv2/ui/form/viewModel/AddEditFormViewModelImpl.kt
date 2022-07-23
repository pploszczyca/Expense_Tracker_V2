package com.example.expensetrackerv2.ui.form.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.database.models.ExpenseConstants
import com.example.expensetrackerv2.database.models.TypeOfExpense
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.ui.form.mappers.AddEditFormStateMapper
import com.example.expensetrackerv2.use_cases.expense.*
import com.example.expensetrackerv2.use_cases.type_of_expense.GetTypesOfExpense
import com.example.expensetrackerv2.utilities.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditFormViewModelImpl @Inject constructor(
    private val getExpenseWithItsType: GetExpenseWithItsType,
    private val insertExpenseWithItsType: InsertExpenseWithItsType,
    private val updateExpenseWithItsType: UpdateExpenseWithItsType,
    private val getExpensesTitles: GetExpensesTitles,
    private val getExpensesPlaces: GetExpensesPlaces,
    private val getTypesOfExpense: GetTypesOfExpense,
    private val addEditFormStateMapper: AddEditFormStateMapper = AddEditFormStateMapper()
) : AddEditFormViewModel() {
    private var state by mutableStateOf(State())

    override val viewState: androidx.compose.runtime.State<ViewState>
        get() = mutableStateOf(addEditFormStateMapper.toViewState(state))

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getExpensesTitles().collect {
                state = state.copy(
                    expensesTitles = it
                )
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            getExpensesPlaces().collect {
                state = state.copy(
                    expensesPlaces = it
                )
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            getTypesOfExpense().collect {
                state = state.copy(
                    typeOfExpenses = it
                )
            }
        }
    }

    override fun onEvent(event: Event) {
        when (event) {
            is Event.FormSubmit -> formSubmit()
            else -> state = when (event) {
                is Event.IdChange -> {
                    loadExpenseWithItsType(event.id)
                    state.copy(id = event.id)
                }
                is Event.TitleChange -> state.copy(title = event.title)
                is Event.PriceChange -> state.copy(price = event.price)
                is Event.DateChange -> state.copy(date = DateUtils.stringToDate(event.date))
                is Event.PlaceChange -> state.copy(place = event.place)
                is Event.DescriptionChange -> state.copy(place = event.description)
                is Event.TypeOfAddEditChange -> state.copy(selectedTypeOfExpenseId = event.id)
                else -> state
            }
        }
    }

    fun formSubmit() {
        insertOrUpdateNewExpense(addEditFormStateMapper.toExpenseWithItsType(state))
    }

    private fun insertOrUpdateNewExpense(newExpenseWithItsType: ExpenseWithItsType) {
        viewModelScope.launch(Dispatchers.IO) {
            when (state.isNewExpense) {
                true -> insertExpenseWithItsType(newExpenseWithItsType)
                false -> updateExpenseWithItsType(newExpenseWithItsType)
            }
        }
    }

    private fun loadExpenseWithItsType(expenseID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getExpenseWithItsType(expenseID).collect {
                val expenseWithItsType = it ?: ExpenseWithItsType()
                changeFormStates(expenseWithItsType)
            }
        }
    }

    private fun changeFormStates(expenseWithItsType: ExpenseWithItsType) {
        listOf(
            Event.TitleChange(expenseWithItsType.title),
            Event.PriceChange(if (expenseWithItsType.price != 0.0) expenseWithItsType.price.toString() else ""),
            Event.DateChange(DateUtils.toOnlyDateString(expenseWithItsType.date)),
            Event.PlaceChange(expenseWithItsType.place),
            Event.DescriptionChange(expenseWithItsType.description),
            Event.TypeOfAddEditChange(expenseWithItsType.id)
        ).forEach { event -> onEvent(event) }
    }

    data class State(
        val id: Int = ExpenseConstants.NEW_EXPENSE_ID,
        val title: String = "",
        val price: String = "",
        val selectedTypeOfExpenseId: Int = -1,
        val date: Date = Date(),
        val place: String = "",
        val description: String = "",
        val expensesTitles: List<String>? = null,
        val expensesPlaces: List<String>? = null,
        val typeOfExpenses: List<TypeOfExpense>? = null
    ) {
        val isNewExpense get() = id == ExpenseConstants.NEW_EXPENSE_ID
    }
}