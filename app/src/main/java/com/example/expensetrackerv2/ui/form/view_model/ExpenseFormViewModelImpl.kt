package com.example.expensetrackerv2.ui.form.view_model

import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.use_cases.category.GetCategory
import com.example.expensetrackerv2.use_cases.expense.GetExpenseWithCategory
import com.example.expensetrackerv2.use_cases.expense.GetExpensesPlaces
import com.example.expensetrackerv2.use_cases.expense.GetExpensesTitles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

//@HiltViewModel
class ExpenseFormViewModelImpl(
    private val inputData: InputData,
    getExpensesTitles: GetExpensesTitles,
    getExpensesPlaces: GetExpensesPlaces,
    getCategory: GetCategory,
    getExpenseWithCategory: GetExpenseWithCategory,
) : ExpenseFormViewModel() {

    private val _viewState: MutableStateFlow<ViewState?> = MutableStateFlow(null)
    val viewState: StateFlow<ViewState?> = _viewState

    init {
        val getExpenseOrNullFlow: Flow<ExpenseWithCategory?> =
            when (inputData.expenseId) {
                null -> flowOf(null)
                else -> getExpenseWithCategory(inputData.expenseId)
            }

        viewModelScope.launch {
            combine(
                getExpensesTitles(),
                getExpensesPlaces(),
                getCategory(),
                getExpenseOrNullFlow,
            ) { titles, places, categories, expense ->
                val chosenCategory = when (expense) {
                    null -> categories.first().let { ViewState.Category(it.id, it.name) }
                    else -> ViewState.Category(expense.categoryId, expense.categoryName)
                }

                val submitButtonTextId = when (expense == null) {
                    true -> R.string.add
                    false -> R.string.update
                }

                return@combine ViewState(
                    title = expense?.title.orEmpty(),
                    price = expense?.price?.toString().orEmpty(),
                    chosenCategory = chosenCategory,
                    date = expense?.date.toString(),
                    placeName = expense?.place.orEmpty(),
                    description = expense?.description.orEmpty(),
                    previousTitles = titles,
                    previousPlaceNames = places,
                    categories = categories.map { ViewState.Category(it.id, it.name) },
                    submitButtonText = submitButtonTextId
                )
            }.flowOn(Dispatchers.IO)
                .collect { formViewState ->
                    _viewState.update { formViewState }
                }
        }
    }

    override fun onTitleChanged(title: String) {
        _viewState.update {
            it?.copy(title = title)
        }
    }

    override fun onPriceChanged(price: String) {
        _viewState.update {
            it?.copy(price = price)
        }
    }

    override fun onCategoryChanged(categoryId: Int) {
        _viewState.update {
            it?.copy(chosenCategory = it.categories.first { category -> category.id == categoryId })
        }
    }

    override fun onDateChanged(date: LocalDate) {
        _viewState.update {
            it?.copy(date = date.toString())
        }
    }

    override fun onPlaceNameChanged(placeName: String) {
        _viewState.update {
            it?.copy(placeName = placeName)
        }
    }

    override fun onDescriptionChanged(description: String) {
        _viewState.update {
            it?.copy(description = description)
        }
    }

    override fun onSubmitButtonClicked() {
        TODO("Not yet implemented")
    }
}