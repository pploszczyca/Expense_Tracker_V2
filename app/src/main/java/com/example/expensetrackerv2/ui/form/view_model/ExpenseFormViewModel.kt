package com.example.expensetrackerv2.ui.form.view_model

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.example.expensetrackerv2.R
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

abstract class ExpenseFormViewModel : ViewModel() {

    abstract val viewState: StateFlow<ViewState>

    abstract fun onTitleChanged(title: String)
    abstract fun onPriceChanged(price: String)
    abstract fun onCategoryChanged(categoryId: Int)
    abstract fun onDateChanged(date: LocalDate)
    abstract fun onPlaceNameChanged(placeName: String)
    abstract fun onDescriptionChanged(description: String)
    abstract fun onSubmitButtonClicked()

    data class ViewState(
        val title: String = "",
        val price: String = "",
        val chosenCategoryId: Int = -1,
        val date: String = "",
        val placeName: String = "",
        val description: String = "",
        val previousTitles: List<String> = emptyList(),
        val previousPlaceNames: List<String> = emptyList(),
        val categories: List<Category> = emptyList(),
        @StringRes
        val submitButtonText: Int = R.string.add,
    ) {
        data class Category(
            val id: Int = -1,
            val name: String = "",
            val isSelected: Boolean = false,
        )
    }
}



