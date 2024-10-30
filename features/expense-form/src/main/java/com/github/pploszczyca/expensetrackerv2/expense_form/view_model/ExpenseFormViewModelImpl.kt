package com.github.pploszczyca.expensetrackerv2.expense_form.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.pploszczyca.expensetrackerv2.common_kotlin.coroutines.DispatcherProvider
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.toDate
import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.toFormattedString
import com.github.pploszczyca.expensetrackerv2.usecases.category.GetCategories
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpensesPlaces
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpensesTitles
import com.github.pploszczyca.expensetrackerv2.usecases.expense.InsertExpense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.UpdateExpense
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.domain.orZero
import com.github.pploszczyca.expensetrackerv2.features.expense_form.R
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
    private val dispatcherProvider: DispatcherProvider,
    private val navigationRouter: NavigationRouter,
) : ExpenseFormViewModel() {

    private lateinit var _categories: List<Category>

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    override val viewState: StateFlow<ViewState> = _viewState

    private val _routeActions: MutableSharedFlow<RouteAction> = MutableSharedFlow()
    override val routeActions: SharedFlow<RouteAction> = _routeActions

    private val expenseId: Id? = savedStateHandle.get<String>("EXPENSE_ID")?.let(Id::from)

    init {
        viewModelScope.launch(dispatcherProvider.default) {
            val getExpenseOrNullFlow: Flow<Expense?> =
                expenseId?.let { getExpense(it) } ?: flowOf(null)

            combine(
                getExpensesTitles(),
                getExpensesPlaces(),
                getCategories(),
                getExpenseOrNullFlow,
            ) { titles, places, categories, expense ->
                _categories = categories

                val chosenCategoryId: Id? = when (expense) {
                    null -> categories.firstOrNull()?.id
                    else -> expense.category?.id
                }

                val submitButtonTextId = when (expense == null) {
                    true -> R.string.add
                    false -> R.string.update
                }

                return@combine when (expense) {
                    null -> ViewState(
                        isLoading = false,
                        previousTitles = titles,
                        previousPlaceNames = places,
                        categories = mapToViewStateCategories(categories, chosenCategoryId),
                        submitButtonText = submitButtonTextId,
                    )

                    else -> ViewState(
                        isLoading = false,
                        title = expense.title,
                        price = expense.price,
                        chosenCategoryId = chosenCategoryId,
                        date = expense.date,
                        placeName = expense.place,
                        description = expense.description,
                        previousTitles = titles,
                        previousPlaceNames = places,
                        categories = mapToViewStateCategories(categories, chosenCategoryId),
                        submitButtonText = submitButtonTextId,
                    )
                }
            }.collect { formViewState ->
                _viewState.update { formViewState }
            }
        }
    }

    private fun mapToViewStateCategories(
        categories: List<Category>,
        chosenCategoryId: Id?,
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
            it.copy(price = Price.of(price))
        }
    }

    override fun onCategoryChanged(categoryId: Id?) {
        _viewState.update {
            it.copy(
                chosenCategoryId = categoryId,
                categories = it.categories.map { category -> category.copy(isSelected = category.id == categoryId) }
            )
        }
    }

    override fun onDateChanged(date: ExpenseDate) {
        _viewState.update {
            it.copy(date = date)
        }
    }

    override fun onPlaceNameChanged(placeName: String) {
        _viewState.update {
            it.copy(
                placeName = when (placeName) {
                    "" -> null
                    else -> placeName
                }
            )
        }
    }

    override fun onDescriptionChanged(description: String) {
        _viewState.update {
            it.copy(
                description = when (description) {
                    "" -> null
                    else -> description
                }
            )
        }
    }

    override fun onSubmitButtonClicked() {
        viewModelScope.launch(dispatcherProvider.default) {
            if (viewState.value.isAllDataValidated().not()) {
                _routeActions.emit(RouteAction.ShowSnackBar)
                return@launch
            }

            when (expenseId == null) {
                true -> performInsertingExpense()
                false -> performUpdatingExpense(expenseId)
            }

            withContext(dispatcherProvider.main) {
                navigationRouter.goBack()
            }
        }
    }

    private fun ViewState.isAllDataValidated(): Boolean =
        title != "" && price != Price.ZERO

    private suspend fun performInsertingExpense() {
        with(viewState.value) {
            insertExpense(
                title = title,
                price = price,
                date = date,
                description = description,
                place = placeName,
                category = chosenCategory(),
                type = type,
            )
        }
    }

    private suspend fun performUpdatingExpense(expenseId: Id) {
        with(viewState.value) {
            updateExpense(
                id = expenseId,
                title = title,
                price = price,
                date = date,
                description = description,
                place = placeName,
                category = chosenCategory(),
                type = type,
            )
        }
    }

    private fun ViewState.chosenCategory(): Category? =
        chosenCategoryId?.let { _categories.firstOrNull { it.id == chosenCategoryId } }

    override fun onBackClicked() {
        viewModelScope.launch(dispatcherProvider.default) {
            navigationRouter.goBack()
        }
    }
}