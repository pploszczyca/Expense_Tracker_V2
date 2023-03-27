package com.example.expensetrackerv2.ui.form.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.models.CategoryEntity
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
import javax.inject.Inject

@HiltViewModel
class ExpenseFormViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getExpensesTitles: GetExpensesTitles,
    getExpensesPlaces: GetExpensesPlaces,
    getCategory: GetCategory,
    getExpenseWithCategory: GetExpenseWithCategory,
) : ExpenseFormViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    override val viewState: StateFlow<ViewState> = _viewState

    private val expenseId: Int? = savedStateHandle.get<Int>("expenseId")

    init {
        val getExpenseOrNullFlow: Flow<ExpenseWithCategory?> =
            when (expenseId) {
                null -> flowOf(null)
                else -> getExpenseWithCategory(expenseId)
            }

        viewModelScope.launch {
            combine(
                getExpensesTitles(),
                getExpensesPlaces(),
                getCategory(),
                getExpenseOrNullFlow,
            ) { titles, places, categories, expense ->
                val chosenCategoryId = when (expense) {
                    null -> categories.first().id
                    else -> expense.categoryId
                }

                val submitButtonTextId = when (expense == null) {
                    true -> R.string.add
                    false -> R.string.update
                }

                return@combine ViewState(
                    title = expense?.title.orEmpty(),
                    price = expense?.price?.toString().orEmpty(),
                    chosenCategoryId = chosenCategoryId,
                    date = expense?.date.toString(),
                    placeName = expense?.place.orEmpty(),
                    description = expense?.description.orEmpty(),
                    previousTitles = titles,
                    previousPlaceNames = places,
                    categories = mapToViewStateCategories(categories, chosenCategoryId),
                    submitButtonText = submitButtonTextId
                )
            }.flowOn(Dispatchers.IO)
                .collect { formViewState ->
                    _viewState.update { formViewState }
                }
        }
    }

    private fun mapToViewStateCategories(
        categories: List<CategoryEntity>,
        chosenCategoryId: Int,
    ): List<ViewState.Category> =
        categories.map {
            ViewState.Category(
                id = it.id,
                name = it.name,
                isSelected = it.id == chosenCategoryId,
            )
        }

    override fun onTitleChanged(title: String) {
        _viewState.update {
            it.copy(title = title)
        }
    }

    override fun onPriceChanged(price: String) {
        _viewState.update {
            it.copy(price = price)
        }
    }

    override fun onCategoryChanged(categoryId: Int) {
        _viewState.update {
            it.copy(
                chosenCategoryId = categoryId,
                categories = it.categories.map { category -> category.copy(isSelected = category.id == categoryId) }
            )
        }
    }

    override fun onDateChanged(date: LocalDate) {
        _viewState.update {
            it.copy(date = date.toString())
        }
    }

    override fun onPlaceNameChanged(placeName: String) {
        _viewState.update {
            it.copy(placeName = placeName)
        }
    }

    override fun onDescriptionChanged(description: String) {
        _viewState.update {
            it.copy(description = description)
        }
    }

    override fun onSubmitButtonClicked() {
        TODO("Not yet implemented")
    }
}