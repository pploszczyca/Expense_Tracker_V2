package com.github.pploszczyca.expensetrackerv2.expense_form.view_model

import androidx.lifecycle.SavedStateHandle
import com.github.pploszczyca.expensetrackerv2.features.expense_form.R
import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.toDate
import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.toFormattedString
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import com.github.pploszczyca.expensetrackerv2.usecases.category.GetCategories
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpensesPlaces
import com.github.pploszczyca.expensetrackerv2.usecases.expense.GetExpensesTitles
import com.github.pploszczyca.expensetrackerv2.usecases.expense.InsertExpense
import com.github.pploszczyca.expensetrackerv2.usecases.expense.UpdateExpense
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.mockk.*
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

    val testDispatcher = StandardTestDispatcher()

    beforeAny {
        Dispatchers.setMain(testDispatcher)
    }

    afterAny {
        Dispatchers.resetMain()
    }

    // Needed to run all UCs for example just after VM initialization
    fun runAllAsynchronousTasks() {
        testDispatcher.scheduler.advanceUntilIdle()
    }

    val savedStateHandle: SavedStateHandle = mockk()
    val getExpensesTitles: GetExpensesTitles = mockk()
    val getExpensesPlaces: GetExpensesPlaces = mockk()
    val getCategories: GetCategories = mockk()
    val getExpense: GetExpense = mockk()
    val insertExpense: InsertExpense = mockk()
    val updateExpense: UpdateExpense = mockk()
    val navigationRouter: NavigationRouter = mockk {
        every { goBack() } returns Unit
    }

    fun tested(
        savedStateHandle: SavedStateHandle = mockk(),
        getExpensesTitles: GetExpensesTitles = mockk(),
        getExpensesPlaces: GetExpensesPlaces = mockk(),
        getCategories: GetCategories = mockk(),
        getExpense: GetExpense = mockk(),
        insertExpense: InsertExpense = mockk(),
        updateExpense: UpdateExpense = mockk(),
        navigationRouter: NavigationRouter = mockk(),
    ): ExpenseFormViewModel =
        ExpenseFormViewModelImpl(
            savedStateHandle = savedStateHandle,
            getExpensesTitles = getExpensesTitles,
            getExpensesPlaces = getExpensesPlaces,
            getCategories = getCategories,
            getExpense = getExpense,
            insertExpense = insertExpense,
            updateExpense = updateExpense,
            ioDispatcher = testDispatcher,
            navigationRouter = navigationRouter,
        ).apply {
            runAllAsynchronousTasks()
        }

    Given("New expense Id is provided") {
        val expenseId = ExpenseFormViewModel.NO_EXPENSE_ID
        val titles: List<String> = mockk()
        val places: List<String> = mockk()
        val categoryId = 78
        val categoryName = "categoryName"
        val secondCategoryId = 90
        val secondCategoryName = "secondCategoryName"
        val category = Category(
            id = categoryId,
            name = categoryName,
            type = Category.Type.INCOME,
        )
        val secondCategory = Category(
            id = secondCategoryId,
            name = secondCategoryName,
            type = Category.Type.INCOME,
        )
        val categories: List<Category> = listOf(category, secondCategory)
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

        forAll(
            row("", ""),
            row("title", ""),
            row("", "2.00")
        ) { title, price ->
            And("Data are not valid (title: $title, price: $price)") {
                When("Submit button is clicked") {
                    val routeActions = tested(
                        savedStateHandle = savedStateHandle,
                        getExpensesTitles = getExpensesTitles,
                        getExpensesPlaces = getExpensesPlaces,
                        getCategories = getCategories,
                        insertExpense = insertExpense,
                    ).apply {
                        onTitleChanged(title)
                        onPriceChanged(price)
                        onSubmitButtonClicked()
                    }.routeActions.first()

                    Then("SnackBar should be showed") {
                        routeActions shouldBe ExpenseFormViewModel.RouteAction.ShowSnackBar
                    }

                    Then("Insert new expense should not be executed") {
                        verify { insertExpense wasNot Called }
                    }
                }
            }
        }

        And("Data for new expense are valid") {
            val title = "New title"
            val price = "50.00"
            val stringDate = "2000-06-12"
            val date = LocalDate.parse(stringDate)
            val placeName = "new placeName"
            val description = "new description"

            coEvery { insertExpense(any(), any(), any(), any(), any(), any()) } returns Unit

            When("Data are valid") {
                And("Submit button is clicked") {
                    tested(
                        savedStateHandle = savedStateHandle,
                        getExpensesTitles = getExpensesTitles,
                        getExpensesPlaces = getExpensesPlaces,
                        getCategories = getCategories,
                        insertExpense = insertExpense,
                        navigationRouter = navigationRouter,
                    ).apply {
                        onTitleChanged(title)
                        onPriceChanged(price)
                        onDateChanged(date)
                        onCategoryChanged(secondCategoryId)
                        onPlaceNameChanged(placeName)
                        onDescriptionChanged(description)
                        onSubmitButtonClicked()
                        runAllAsynchronousTasks()
                    }

                    Then("New expense should be inserted") {
                        coVerify {
                            insertExpense(
                                title = title,
                                price = 50.00,
                                date = stringDate.toDate(),
                                place = placeName,
                                description = description,
                                category = secondCategory,
                            )
                        }
                    }

                    Then("Go back") {
                        coVerify { navigationRouter.goBack() }
                    }
                }
            }
        }

        When("Back button is clicked") {
            tested(
                savedStateHandle = savedStateHandle,
                getExpensesTitles = getExpensesTitles,
                getExpensesPlaces = getExpensesPlaces,
                getCategories = getCategories,
                navigationRouter = navigationRouter,
            ).apply {
                onBackClicked()
            }

            Then("Go back") {
                coVerify { navigationRouter.goBack() }
            }
        }
    }

    Given("Expense to update") {
        val titles: List<String> = mockk()
        val places: List<String> = mockk()
        val categoryId = 78
        val categoryName = "categoryName"
        val secondCategoryId = 90
        val secondCategoryName = "secondCategoryName"
        val category = Category(
            id = categoryId,
            name = categoryName,
            type = Category.Type.INCOME,
        )
        val secondCategory = Category(
            id = secondCategoryId,
            name = secondCategoryName,
            type = Category.Type.INCOME,
        )
        val categories: List<Category> = listOf(category, secondCategory)
        val expenseId = 9
        val title = "expenseTitle"
        val price = 50.00
        val date = Date()
        val description = "expenseDescription"
        val place = "expensePlace"
        val expense = Expense(
            id = expenseId,
            title = title,
            price = price,
            date = date,
            description = description,
            place = place,
            category = category,
        )
        val viewState = ExpenseFormViewModel.ViewState(
            title = title,
            price = price.toString(),
            chosenCategoryId = categoryId,
            date = Date().toFormattedString(),
            placeName = place,
            description = description,
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
            submitButtonText = R.string.update,
        )

        every { savedStateHandle.get<Int>("EXPENSE_ID") } returns expenseId
        every { getExpensesTitles() } returns flowOf(titles)
        every { getExpensesPlaces() } returns flowOf(places)
        every { getCategories() } returns flowOf(categories)
        every { getExpense(any()) } returns flowOf(expense)

        When("View model is initialized") {
            val actualViewState = tested(
                savedStateHandle = savedStateHandle,
                getExpensesTitles = getExpensesTitles,
                getExpensesPlaces = getExpensesPlaces,
                getCategories = getCategories,
                getExpense = getExpense,
            ).viewState.value

            Then("View state will be set up for updating expense") {
                verify { getExpense(expenseId) }

                actualViewState shouldBe viewState
            }
        }

        And("Data for updating expense are valid") {
            val newTitle = "New title"
            val newPrice = "50.00"
            val newStringDate = "2000-06-12"
            val newDate = LocalDate.parse(newStringDate)
            val newPlaceName = "new placeName"
            val newDescription = "new description"

            coEvery { updateExpense(any(), any(), any(), any(), any(), any(), any()) } returns Unit

            When("Data are valid") {
                And("Submit button is clicked") {
                    tested(
                        savedStateHandle = savedStateHandle,
                        getExpensesTitles = getExpensesTitles,
                        getExpensesPlaces = getExpensesPlaces,
                        getCategories = getCategories,
                        getExpense = getExpense,
                        updateExpense = updateExpense,
                        navigationRouter = navigationRouter,
                    ).apply {
                        onTitleChanged(newTitle)
                        onPriceChanged(newPrice)
                        onDateChanged(newDate)
                        onCategoryChanged(secondCategoryId)
                        onPlaceNameChanged(newPlaceName)
                        onDescriptionChanged(newDescription)
                        onSubmitButtonClicked()
                        runAllAsynchronousTasks()
                    }

                    Then("New expense should be inserted") {
                        coVerify {
                            updateExpense(
                                id = expenseId,
                                title = newTitle,
                                price = 50.00,
                                date = newStringDate.toDate(),
                                place = newPlaceName,
                                description = newDescription,
                                category = secondCategory,
                            )
                        }
                    }

                    Then("Go back") {
                        coVerify { navigationRouter.goBack() }
                    }
                }
            }
        }
    }
})
