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
import kotlinx.coroutines.flow.first
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
) : AddEditFormViewModel() {
    private val addEditFormStateMapper: AddEditFormStateMapper = AddEditFormStateMapper()

    private var state by mutableStateOf(State())

    override val viewState: ViewState
        get() = addEditFormStateMapper.toViewState(state)

    init {
        viewModelScope.launch(Dispatchers.Main) {
            getExpensesTitles().collect {
                state = state.copy(
                    expensesTitles = it
                )
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            getExpensesPlaces().collect {
                state = state.copy(
                    expensesPlaces = it
                )
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
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
            is Event.IdChange -> loadExpenseWithItsType(event.id)
            else -> state = when (event) {
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

    private fun formSubmit() {
        insertOrUpdateNewExpense(addEditFormStateMapper.toExpenseWithItsType(state))
    }

    private fun insertOrUpdateNewExpense(newExpenseWithItsType: ExpenseWithItsType) {
        viewModelScope.launch(Dispatchers.Main) {
            when (state.isNewExpense) {
                true -> insertExpenseWithItsType(newExpenseWithItsType)
                false -> updateExpenseWithItsType(newExpenseWithItsType)
            }
        }
    }

    private fun loadExpenseWithItsType(expenseID: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val expenseWithItsType = getExpenseWithItsType(expenseID).first()
            changeFormStates(
                expenseWithItsType = expenseWithItsType ?: ExpenseWithItsType()
            )
        }
    }

    private fun changeFormStates(expenseWithItsType: ExpenseWithItsType) {
        state = state.copy(
            id = expenseWithItsType.id,
            title = expenseWithItsType.title,
            price = if (expenseWithItsType.price != 0.0) expenseWithItsType.price.toString() else "",
            date = expenseWithItsType.date,
            place = expenseWithItsType.place,
            description = expenseWithItsType.description,
            selectedTypeOfExpenseId = expenseWithItsType.typeID
        )
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