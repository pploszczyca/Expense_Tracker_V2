package com.github.pploszczyca.expensetrackerv2.expense_form.view_model

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.features.expense_form.R
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class ExpenseFormViewModel : ViewModel() {

    abstract val viewState: StateFlow<ViewState>
    abstract val routeActions: SharedFlow<RouteAction>

    abstract fun onTitleChanged(title: String)
    abstract fun onPriceChanged(price: String)
    abstract fun onIncomeValueChanged()
    abstract fun onOutgoValueChanged()
    abstract fun onCategoryChanged(categoryId: Id?)
    abstract fun onDateChanged(date: ExpenseDate)
    abstract fun onPlaceNameChanged(placeName: String)
    abstract fun onDescriptionChanged(description: String)
    abstract fun onSubmitButtonClicked()
    abstract fun onBackClicked()

    data class ViewState(
        val isLoading: Boolean = true,
        val title: String = "",
        val price: Price = Price.ZERO,
        val chosenCategoryId: Id? = null,
        val date: ExpenseDate = ExpenseDate.now(),
        val placeName: String? = null,
        val description: String? = null,
        val previousTitles: List<String> = emptyList(),
        val previousPlaceNames: List<String> = emptyList(),
        val categories: List<Category> = emptyList(),
        val type: Expense.Type = Expense.Type.Outgo,
        @StringRes
        val submitButtonText: Int = R.string.add,
        val shouldOpenKeyboard: Boolean = false,
    ) {
        data class Category(
            val id: Id = Id.NO_ID,
            val name: String = "",
            val isSelected: Boolean = false,
        )
    }

    sealed interface RouteAction {
        object ShowSnackBar : RouteAction
    }
}



