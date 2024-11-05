package com.github.pploszczyca.expensetrackerv2.expense_form.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.pploszczyca.expensetrackerv2.common_kotlin.coroutines.DispatcherProvider
import com.github.pploszczyca.expensetrackerv2.common_kotlin.currencyFormatter.CurrencyFormatter
import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.updateTransform
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.features.expense_form.R
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import com.github.pploszczyca.expensetrackerv2.usecases.category.GetCategories
import com.github.pploszczyca.expensetrackerv2.usecases.expense.DeleteExpense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpensesPlaces
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpensesTitles
import com.github.pploszczyca.expensetrackerv2.usecases.expense.InsertExpense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.UpdateExpense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
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
    private val currencyFormatter: CurrencyFormatter,
    private val deleteExpense: DeleteExpense,
) : ExpenseFormViewModel() {

    private lateinit var _categories: List<Category>

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    override val viewState: StateFlow<ViewState> = _viewState

    private val _routeActions: MutableSharedFlow<RouteAction> = MutableSharedFlow()
    override val routeActions: SharedFlow<RouteAction> = _routeActions

    private val _expense: MutableStateFlow<Expense?> = MutableStateFlow(null)
    private val expenseId: Id? = savedStateHandle.get<String>("EXPENSE_ID")?.let(Id::from)

    init {
        viewModelScope.launch(dispatcherProvider.default) {
            val expense: Expense? = expenseId?.let { getExpense(it) }
            val categories: List<Category> = getCategories()
            val previousTitles = getExpensesTitles()
            val previousPlaceNames = getExpensesPlaces()

            _categories = categories
            _expense.value = expense

            val chosenCategoryId: Id? = expense?.category?.id

            val submitButtonTextId = when (expense == null) {
                true -> R.string.add
                false -> R.string.update
            }

            _viewState.updateTransform {
                when (expense) {
                    null -> copy(
                        isLoading = false,
                        categories = mapToViewStateCategories(categories, null),
                        submitButtonText = submitButtonTextId,
                        previousTitles = previousTitles,
                        previousPlaceNames = previousPlaceNames,
                        shouldOpenKeyboard = true,
                    )

                    else -> copy(
                        isLoading = false,
                        title = expense.title,
                        price = expense.price,
                        chosenCategoryId = chosenCategoryId,
                        date = expense.date,
                        placeName = expense.place,
                        description = expense.description,
                        categories = mapToViewStateCategories(categories, chosenCategoryId),
                        submitButtonText = submitButtonTextId,
                        type = expense.type,
                        shouldShowDeleteButton = true,
                        previousTitles = previousTitles,
                        previousPlaceNames = previousPlaceNames,
                    )
                }
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
            it.copy(price = price.let(currencyFormatter::format).let(Price::of))
        }
    }

    override fun onIncomeValueChanged() {
        _viewState.updateTransform {
            copy(type = Expense.Type.Income)
        }
    }

    override fun onOutgoValueChanged() {
        _viewState.updateTransform {
            copy(type = Expense.Type.Outgo)
        }
    }

    override fun onCategoryChanged(categoryId: Id?) {
        _viewState.updateTransform {
            val chosenCategoryId =
                categoryId?.takeUnless { categories.any { it.id == categoryId && it.isSelected } }
            copy(
                chosenCategoryId = chosenCategoryId,
                categories = categories.map { category -> category.copy(isSelected = (category.id == chosenCategoryId)) }
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
            Timber.d("Submit button clicked")
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
        Timber.d("Inserting expense")
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

    override fun onDeleteButtonClicked() {
        _viewState.update {
            it.copy(shouldShowDeleteDialog = true)
        }
    }

    override fun onDismissDeleteDialog() {
        _viewState.update {
            it.copy(shouldShowDeleteDialog = false)
        }
    }

    override fun onDeleteConfirmed() {
        viewModelScope.launch(dispatcherProvider.default) {
            _expense.value?.let { expense ->
                _viewState.update {
                    it.copy(shouldShowDeleteDialog = false)
                }
                deleteExpense(expense)
            }

            navigationRouter.goBack()
        }
    }
}