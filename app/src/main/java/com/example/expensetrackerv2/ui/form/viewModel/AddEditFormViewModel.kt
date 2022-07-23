package com.example.expensetrackerv2.ui.form.viewModel

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.expensetrackerv2.database.models.TypeOfExpense
import java.util.*

abstract class AddEditFormViewModel: ViewModel() {
    abstract val viewState: State<ViewState>

    abstract fun onEvent(event: Event)

    data class ViewState(
        val id: String,
        val title: String,
        val price: String,
        val typesOfExpense: List<TypeOfExpenseRecord>,
        val date: Date,
        val place: String,
        val description: String,
        val titleSuggestions: List<String>,
        val placeSuggestions: List<String>,
    ) {
        data class TypeOfExpenseRecord(
            val id: Int,
            val name: String,
            val clicked: Boolean
        )
    }

    sealed class Event {
        data class IdChange(val id: Int) : Event()
        data class TitleChange(val title: String) : Event()
        data class PriceChange(val price: String) : Event()
        data class TypeOfAddEditChange(val id: Int) : Event()
        data class DateChange(val value: String) : Event()
        data class PlaceChange(val value: String) : Event()
        data class DescriptionChange(val value: String) : Event()
        object FormSubmit: Event()
    }
}