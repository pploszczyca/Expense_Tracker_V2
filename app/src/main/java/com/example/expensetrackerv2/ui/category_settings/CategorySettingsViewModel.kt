package com.example.expensetrackerv2.ui.category_settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerv2.models.CategoryConstants
import com.example.expensetrackerv2.use_cases.category.DeleteCategory
import com.example.expensetrackerv2.use_cases.category.GetCategories
import com.example.expensetrackerv2.use_cases.category.InsertCategory
import com.example.expensetrackerv2.use_cases.category.UpdateCategory
import com.github.pploszczyca.expensetrackerv2.domain.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategorySettingsViewModel @Inject constructor(
    getCategories: GetCategories,
    private val insertCategory: InsertCategory,
    private val updateCategory: UpdateCategory,
    private val deleteCategory: DeleteCategory,
) : ViewModel() {
    val categories: Flow<List<Category>> = getCategories()

    private val _id = mutableStateOf(CategoryConstants.NEW_TYPE_OF_EXPENSE_ID)
    val id: State<Int> = _id

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _categoryType = mutableStateOf(Category.Type.INCOME)
    val categoryType: State<Category.Type> = _categoryType

    private val _isDialogFormVisible = mutableStateOf(false)
    val isDialogFormVisible: State<Boolean> = _isDialogFormVisible

    private val _isDeleteDialogFormVisible = mutableStateOf(false)
    val isDeleteDialogFormVisible: State<Boolean> = _isDeleteDialogFormVisible

    fun onEvent(event: CategorySettingsEvent) {
        when (event) {
            is CategorySettingsEvent.IdChange -> _id.value = event.value
            is CategorySettingsEvent.NameChange -> _name.value = event.value
            is CategorySettingsEvent.TypeChange -> _categoryType.value = event.value
            is CategorySettingsEvent.CloseDeleteDialog -> closeDialog(
                _isDeleteDialogFormVisible
            )

            is CategorySettingsEvent.CloseFormDialog -> closeDialog(_isDialogFormVisible)
            is CategorySettingsEvent.OpenDeleteDialog -> {
                setIdNameAndType(event.value)
                openDialog(_isDeleteDialogFormVisible)
            }

            is CategorySettingsEvent.OpenFormDialog -> {
                setIdNameAndType(event.value)
                openDialog(_isDialogFormVisible)
            }

            is CategorySettingsEvent.DialogFormSubmit -> {
                insertOrUpdate(event.value)
                onEvent(CategorySettingsEvent.CloseFormDialog())
            }

            is CategorySettingsEvent.DeleteDialogSubmit -> {
                delete(makeCategoryFromState())
                onEvent(CategorySettingsEvent.CloseDeleteDialog())
            }
        }
    }

    fun isThisNewTypeOfExpense(): Boolean =
        id.value == CategoryConstants.NEW_TYPE_OF_EXPENSE_ID

    private fun openDialog(state: MutableState<Boolean>) {
        state.value = true
    }

    private fun closeDialog(state: MutableState<Boolean>) {
        state.value = false
    }

    private fun setIdNameAndType(category: Category) {
        _id.value = category.id
        _name.value = category.name
        _categoryType.value = category.type
    }

    private fun insertOrUpdate(category: Category) {
        viewModelScope.launch {
            if (isThisNewTypeOfExpense()) {
                insertCategory(category)
            } else {
                updateCategory(category)
            }
        }
    }

    private fun makeCategoryFromState(): Category =
        Category(
            id = id.value,
            name = name.value,
            type = categoryType.value,
        )

    private fun delete(category: Category) {
        viewModelScope.launch {
            deleteCategory(category)
        }
    }
}