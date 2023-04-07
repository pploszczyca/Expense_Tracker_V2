package com.example.expensetrackerv2.ui.form.view_model

import androidx.lifecycle.SavedStateHandle
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.extensions.toFormattedString
import com.example.expensetrackerv2.models.CategoryEntity
import com.example.expensetrackerv2.use_cases.category.GetCategory
import com.example.expensetrackerv2.use_cases.expense.*
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class ExpenseFormViewModelImplTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    coroutineTestScope = true
    coroutineDebugProbes = true

    val testDispatcher = StandardTestDispatcher()

    beforeAny {
        Dispatchers.setMain(testDispatcher)
    }

    afterAny {
        Dispatchers.resetMain()
    }

    val savedStateHandle: SavedStateHandle = mockk()
    val getExpensesTitles: GetExpensesTitles = mockk()
    val getExpensesPlaces: GetExpensesPlaces = mockk()
    val getCategories: GetCategory = mockk()
    val getExpenseWithCategory: GetExpenseWithCategory = mockk()
    val insertExpense: InsertExpense = mockk()
    val updateExpense: UpdateExpense = mockk()

    fun tested(
        savedStateHandle: SavedStateHandle = mockk(),
        getExpensesTitles: GetExpensesTitles = mockk(),
        getExpensesPlaces: GetExpensesPlaces = mockk(),
        getCategories: GetCategory = mockk(),
        getExpenseWithCategory: GetExpenseWithCategory = mockk(),
        insertExpense: InsertExpense = mockk(),
        updateExpense: UpdateExpense = mockk(),
    ): ExpenseFormViewModel =
        ExpenseFormViewModelImpl(
            savedStateHandle = savedStateHandle,
            getExpensesTitles = getExpensesTitles,
            getExpensesPlaces = getExpensesPlaces,
            getCategories = getCategories,
            getExpenseWithCategory = getExpenseWithCategory,
            insertExpense = insertExpense,
            updateExpense = updateExpense,
            ioDispatcher = testDispatcher,
        ).apply {
            // Needed to run all UCs just after VM initialization
            testDispatcher.scheduler.advanceUntilIdle()
        }

    Given("New expense Id is provided") {
        val expenseId = ExpenseFormViewModel.NO_EXPENSE_ID
        val titles: List<String> = mockk()
        val places: List<String> = mockk()
        val categoryId = 78
        val categoryName = "categoryName"
        val category = CategoryEntity(
            id = categoryId,
            name = categoryName,
        )
        val categories: List<CategoryEntity> = listOf(category)
        val viewState = ExpenseFormViewModel.ViewState(
            title = "",
            price = "",
            chosenCategoryId = categoryId,
            date = Date().toFormattedString(),
            placeName = "",
            description = "",
            previousTitles = titles,
            previousPlaceNames = places,
            categories = listOf(ExpenseFormViewModel.ViewState.Category(
                id = categoryId,
                name = categoryName,
                isSelected = true,
            )),
            submitButtonText = R.string.add,
        )

        every { savedStateHandle.get<Int>("EXPENSE_ID") } returns expenseId
        every { getExpensesTitles() } returns flowOf(titles)
        every { getExpensesPlaces() } returns flowOf(places)
        every { getCategories() } returns flowOf(categories)

        When("View model is initialized") {
            val actualViewState = tested(
                savedStateHandle = savedStateHandle,
                getExpensesTitles = getExpensesTitles,
                getExpensesPlaces = getExpensesPlaces,
                getCategories = getCategories,
            ).viewState.value

            Then("View state will be set up for adding expense") {
                actualViewState shouldBe viewState
            }
        }

        When("New title occurs") {
            val title = "new title"

            val actualViewState = tested(
                savedStateHandle = savedStateHandle,
                getExpensesTitles = getExpensesTitles,
                getExpensesPlaces = getExpensesPlaces,
                getCategories = getCategories,
            ).apply {
                onTitleChanged(title)
            }.viewState.value

            Then("Title will be changed") {
                val viewStateWithChangedTitle = viewState.copy(title = title)

                actualViewState shouldBe viewStateWithChangedTitle
            }
        }
    }
})
