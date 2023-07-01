package com.example.expensetrackerv2.ui.form.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.extensions.toDate
import com.example.expensetrackerv2.extensions.toFormattedString
import com.github.pploszczyca.expensetrackerb2.navigation.contract.NavigationRouter
import com.github.pploszczyca.expensetrackerv2.usecases.category.GetCategories
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpensesPlaces
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpensesTitles
import com.github.pploszczyca.expensetrackerv2.usecases.expense.InsertExpense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.UpdateExpense
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ExpenseFormViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getExpensesTitles: GetExpensesTitles,
    getExpensesPlaces: GetExpensesPlaces,
    getCategories: GetCategories,
    getExpense: GetExpense,
    private val insertExpense: InsertExpense,
    private val updateExpense: UpdateExpense,
    private val ioDispatcher: CoroutineDispatcher,
    private val navigationRouter: NavigationRouter,
) : ExpenseFormViewModel() {

    private lateinit var _categories: List<Category>

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    override val viewState: StateFlow<ViewState> = _viewState

    private val _routeActions: MutableSharedFlow<RouteAction> = MutableSharedFlow()
    override val routeActions: SharedFlow<RouteAction> = _routeActions

    private val expenseId: Int? = savedStateHandle.get<Int>("EXPENSE_ID")

    init {
        val getExpenseOrNullFlow: Flow<Expense?> =
            when (expenseId == null || expenseId == NO_EXPENSE_ID) {
                true -> flowOf(null)
                false -> getExpense(expenseId)
            }

        viewModelScope.launch {
            combine(
                getExpensesTitles(),
                getExpensesPlaces(),
                getCategories(),
                getExpenseOrNullFlow,
            ) { titles, places, categories, expense ->
                _categories = categories

                val chosenCategoryId = when (expense) {
                    null -> categories.first().id
                    else -> expense.category.id
                }

                val submitButtonTextId = when (expense == null) {
                    true -> R.string.add
                    false -> R.string.update
                }

                return@combine ViewState(
                    title = expense?.title.orEmpty(),
                    price = expense?.price?.toString().orEmpty(),
                    chosenCategoryId = chosenCategoryId,
                    date = (expense?.date ?: Date()).toFormattedString(),
                    placeName = expense?.place.orEmpty(),
                    description = expense?.description.orEmpty(),
                    previousTitles = titles,
                    previousPlaceNames = places,
                    categories = mapToViewStateCategories(categories, chosenCategoryId),
                    submitButtonText = submitButtonTextId
                )
            }.flowOn(ioDispatcher)
                .collect { formViewState ->
                    _viewState.update { formViewState }
                }
        }
    }

    private fun mapToViewStateCategories(
        categories: List<Category>,
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
        if (viewState.value.isAllDataValidated().not()) {
            viewModelScope.launch {
                _routeActions.emit(RouteAction.ShowSnackBar)
            }
            return
        }

        viewModelScope.launch {
            withContext(ioDispatcher) {
                when (expenseId == null || expenseId == NO_EXPENSE_ID) {
                    true -> performInsertingExpense()
                    false -> performUpdatingExpense(expenseId)
                }
            }

            navigationRouter.goBack()
        }
    }

    private fun ViewState.isAllDataValidated(): Boolean =
        title != "" && price != ""

    private suspend fun performInsertingExpense() {
        with(viewState.value) {
            insertExpense(
                title = title,
                price = price.toDouble(),
                date = date.toDate(),
                description = description,
                place = placeName,
                category = chosenCategory(),
            )
        }
    }

    private suspend fun performUpdatingExpense(expenseId: Int) {
        with(viewState.value) {
            updateExpense(
                id = expenseId,
                title = title,
                price = price.toDouble(),
                date = date.toDate(),
                description = description,
                place = placeName,
                category = chosenCategory(),
            )
        }
    }

    private fun ViewState.chosenCategory(): Category =
        _categories.first { it.id == chosenCategoryId }

    override fun onBackClicked() {
        navigationRouter.goBack()
    }
}