package com.example.expensetrackerv2.ui.form.view_model

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import java.time.LocalDate

abstract class ExpenseFormViewModel : ViewModel() {

    data class InputData(
        val expenseId: Int?
    )

    abstract fun onTitleChanged(title: String)
    abstract fun onPriceChanged(price: String)
    abstract fun onCategoryChanged(categoryId: Int)
    abstract fun onDateChanged(date: LocalDate)
    abstract fun onPlaceNameChanged(placeName: String)
    abstract fun onDescriptionChanged(description: String)
    abstract fun onSubmitButtonClicked()

    data class ViewState(
        val title: String,
        val price: String,
        val chosenCategory: Category,
        val date: String,
        val placeName: String,
        val description: String,
        val previousTitles: List<String>,
        val previousPlaceNames: List<String>,
        val categories: List<Category>,
        @StringRes
        val submitButtonText: Int
    ) {
        data class Category(
            val id: Int,
            val name: String
        )
    }
}



