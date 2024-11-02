package com.github.pploszczyca.expensetrackerv2.features.category_settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pploszczyca.expensetrackerv2.common_kotlin.coroutines.DispatcherProvider
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import com.github.pploszczyca.expensetrackerv2.usecases.category.DeleteCategory
import com.github.pploszczyca.expensetrackerv2.usecases.category.GetCategories
import com.github.pploszczyca.expensetrackerv2.usecases.category.InsertCategory
import com.github.pploszczyca.expensetrackerv2.usecases.category.UpdateCategory
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.usecases.category.ObserveCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategorySettingsViewModel @Inject constructor(
    observeCategories: ObserveCategories,
    private val insertCategory: InsertCategory,
    private val updateCategory: UpdateCategory,
    private val deleteCategory: DeleteCategory,
    private val navigationRouter: NavigationRouter,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {
    val categories: Flow<List<Category>> = observeCategories()

    private val _id: MutableState<Id?> = mutableStateOf(null)
    val id: State<Id?> = _id

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _isDialogFormVisible = mutableStateOf(false)
    val isDialogFormVisible: State<Boolean> = _isDialogFormVisible

    private val _isDeleteDialogFormVisible = mutableStateOf(false)
    val isDeleteDialogFormVisible: State<Boolean> = _isDeleteDialogFormVisible

    fun onEvent(event: CategorySettingsEvent) {
        viewModelScope.launch(dispatcherProvider.default) {
            when (event) {
                is CategorySettingsEvent.IdChange -> _id.value = event.value
                is CategorySettingsEvent.NameChange -> _name.value = event.value
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
                    _name.value = event.name
                    insertOrUpdate(makeCategoryFromState())
                    onEvent(CategorySettingsEvent.CloseFormDialog)
                }

                is CategorySettingsEvent.DeleteDialogSubmit -> {
                    deleteCategory(makeCategoryFromState())
                    onEvent(CategorySettingsEvent.CloseDeleteDialog)
                }

                CategorySettingsEvent.OnBackButtonClicked -> navigationRouter.goBack()
            }
        }
    }

    fun isThisNewCategory(): Boolean =
        id.value == null

    private fun openDialog(state: MutableState<Boolean>) {
        state.value = true
    }

    private fun closeDialog(state: MutableState<Boolean>) {
        state.value = false
    }

    private fun setIdNameAndType(category: Category) {
        _id.value = category.id
        _name.value = category.name
    }

    private suspend fun insertOrUpdate(category: Category) {
        if (isThisNewCategory()) {
            insertCategory(category)
        } else {
            updateCategory(category)
        }
    }

    private fun makeCategoryFromState(): Category =
        when (val id = id.value) {
            null -> Category.new(name = name.value)
            else -> Category(id = id, name = name.value)
        }
}