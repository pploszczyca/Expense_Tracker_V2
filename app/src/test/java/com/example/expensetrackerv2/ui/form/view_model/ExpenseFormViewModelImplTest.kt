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
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import java.time.LocalDate
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
        val secondCategoryId = 90
        val secondCategoryName = "secondCategoryName"
        val category = CategoryEntity(
            id = categoryId,
            name = categoryName,
        )
        val secondCategory = CategoryEntity(
            id = secondCategoryId,
            name = secondCategoryName,
        )
        val categories: List<CategoryEntity> = listOf(category, secondCategory)
        val viewState = ExpenseFormViewModel.ViewState(
            title = "",
            price = "",
            chosenCategoryId = categoryId,
            date = Date().toFormattedString(),
            placeName = "",
            description = "",
            previousTitles = titles,
            previousPlaceNames = places,
            categories = listOf(
                ExpenseFormViewModel.ViewState.Category(
                    id = categoryId,
                    name = categoryName,
                    isSelected = true,
                ),
                ExpenseFormViewModel.ViewState.Category(
                    id = secondCategoryId,
                    name = secondCategoryName,
                    isSelected = false,
                ),
            ),
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

        When("New title will be given") {
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
                val expectedViewState = viewState.copy(title = title)

                actualViewState shouldBe expectedViewState
            }
        }

        When("New price will be given") {
            val price = "2.00"

            val actualViewState = tested(
                savedStateHandle = savedStateHandle,
                getExpensesTitles = getExpensesTitles,
                getExpensesPlaces = getExpensesPlaces,
                getCategories = getCategories,
            ).apply {
                onPriceChanged(price)
            }.viewState.value

            Then("Price will be changed") {
                val expectedViewState = viewState.copy(price = price)

                actualViewState shouldBe expectedViewState
            }
        }

        When("New date will be given") {
            val stringDate = "2000-06-12"
            val date = LocalDate.parse(stringDate)

            val actualViewState = tested(
                savedStateHandle = savedStateHandle,
                getExpensesTitles = getExpensesTitles,
                getExpensesPlaces = getExpensesPlaces,
                getCategories = getCategories,
            ).apply {
                onDateChanged(date)
            }.viewState.value

            Then("Date will be changed") {
                val expectedViewState = viewState.copy(date = stringDate)

                actualViewState shouldBe expectedViewState
            }
        }

        When("New category was chosen") {
            val actualViewState = tested(
                savedStateHandle = savedStateHandle,
                getExpensesTitles = getExpensesTitles,
                getExpensesPlaces = getExpensesPlaces,
                getCategories = getCategories,
            ).apply {
                onCategoryChanged(secondCategoryId)
            }.viewState.value

            Then("Price will be changed") {
                val newCategories = listOf(
                    ExpenseFormViewModel.ViewState.Category(
                        id = categoryId,
                        name = categoryName,
                        isSelected = false,
                    ),
                    ExpenseFormViewModel.ViewState.Category(
                        id = secondCategoryId,
                        name = secondCategoryName,
                        isSelected = true,
                    ),
                )
                val expectedViewState = viewState.copy(
                    categories = newCategories,
                    chosenCategoryId = secondCategoryId
                )

                actualViewState shouldBe expectedViewState
            }
        }

        When("New place name will be given") {
            val placeName = "new placeName"

            val actualViewState = tested(
                savedStateHandle = savedStateHandle,
                getExpensesTitles = getExpensesTitles,
                getExpensesPlaces = getExpensesPlaces,
                getCategories = getCategories,
            ).apply {
                onPlaceNameChanged(placeName)
            }.viewState.value

            Then("Place name will be changed") {
                val expectedViewState = viewState.copy(placeName = placeName)

                actualViewState shouldBe expectedViewState
            }
        }

        When("New description will be given") {
            val description = "new description"

            val actualViewState = tested(
                savedStateHandle = savedStateHandle,
                getExpensesTitles = getExpensesTitles,
                getExpensesPlaces = getExpensesPlaces,
                getCategories = getCategories,
            ).apply {
                onDescriptionChanged(description)
            }.viewState.value

            Then("Description will be changed") {
                val expectedViewState = viewState.copy(description = description)

                actualViewState shouldBe expectedViewState
            }
        }
    }
})
