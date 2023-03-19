package com.example.expensetrackerv2.ui.form.view_model

import com.example.expensetrackerv2.use_cases.category.GetCategory
import com.example.expensetrackerv2.use_cases.expense.GetExpenseWithCategory
import com.example.expensetrackerv2.use_cases.expense.GetExpensesPlaces
import com.example.expensetrackerv2.use_cases.expense.GetExpensesTitles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.util.*

@HiltViewModel
class ExpenseFormViewModelImpl(
    val inputData: InputData,
    private val getExpensesTitles: GetExpensesTitles,
    private val getExpensesPlaces: GetExpensesPlaces,
    private val getCategory: GetCategory,
    private val getExpenseWithCategory: GetExpenseWithCategory,
) : ExpenseFormViewModel() {

    private val _viewState = MutableStateFlow(ViewState.Loading)
    val viewState: StateFlow<ViewState> = _viewState

    init {
        combine(
            getExpensesTitles(),
            getExpensesPlaces(),
            getCategory(),
        ) { titles, places, categories ->

            val chosenCategory = categories.first().let { ViewState.Form.Category(it.id, it.name) }

            _viewState.value = ViewState.Form(
                title = "",
                price = "",
                chosenCategory =,
                date = Date(),
                placeName = "",
                description = "",
                previousTitles = titles,
                previousPlaceNames =
            )
        }
    }

    override fun onTitleChanged(title: String) {
        TODO("Not yet implemented")
    }

    override fun onPriceChanged(price: String) {
        TODO("Not yet implemented")
    }

    override fun onCategoryChanged(categoryId: Int) {
        TODO("Not yet implemented")
    }

    override fun onDateChanged(date: Date) {
        TODO("Not yet implemented")
    }

    override fun onPlaceNameChanged(placeName: String) {
        TODO("Not yet implemented")
    }

    override fun onDescriptionChanged(description: String) {
        TODO("Not yet implemented")
    }
}