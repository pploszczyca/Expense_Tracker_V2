package com.example.expensetrackerv2.ui.form.viewModel

import androidx.lifecycle.ViewModel
import java.util.*

abstract class AddEditFormViewModel : ViewModel() {
    abstract val viewState: ViewState

    abstract fun onEvent(event: Event)

    data class ViewState(
        val title: String,
        val price: String,
        val typeOfExpenseRecords: List<TypeOfExpenseRecord>,
        val date: Date,
        val place: String,
        val description: String,
        val titleSuggestions: List<String>,
        val placeSuggestions: List<String>,
        val isAllDataLoaded: Boolean,
        val buttonTextId: Int
    ) {
        data class TypeOfExpenseRecord(
            val id: Int,
            val name: String,
            val selected: Boolean
        )
    }

    sealed class Event {
        data class IdChange(val id: Int) : Event()
        data class TitleChange(val title: String) : Event()
        data class PriceChange(val price: String) : Event()
        data class TypeOfAddEditChange(val id: Int) : Event()
        data class DateChange(val date: String) : Event()
        data class PlaceChange(val place: String) : Event()
        data class DescriptionChange(val description: String) : Event()
        object FormSubmit : Event()
    }
}