package com.github.pploszczyca.expensetrackerv2.expense_form.view_model

import androidx.lifecycle.SavedStateHandle
import com.github.pploszczyca.expensetrackerv2.common_kotlin.currencyFormatter.CurrencyFormatter
import com.github.pploszczyca.expensetrackerv2.features.expense_form.R
import com.github.pploszczyca.expensetrackerv2.common_test.UnconfinedDispatcherProvider
import com.github.pploszczyca.expensetrackerv2.common_test.dummy
import com.github.pploszczyca.expensetrackerv2.common_test.noOp
import com.github.pploszczyca.expensetrackerv2.navigation.contract.NavigationRouter
import com.github.pploszczyca.expensetrackerv2.usecases.category.GetCategories
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.usecases.expense.DeleteExpense
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
import kotlinx.coroutines.flow.*
import java.util.*
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

class ExpenseFormViewModelImplTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    coroutineTestScope = true

    timeout = 1.seconds.toLong(DurationUnit.MILLISECONDS)

    val savedStateHandle: SavedStateHandle = mockk()
    val getExpensesTitles: GetExpensesTitles = mockk()
    val getExpensesPlaces: GetExpensesPlaces = mockk()
    val getCategories: GetCategories = mockk()
    val getExpense: GetExpense = mockk()
    val insertExpense: InsertExpense = mockk()
    val updateExpense: UpdateExpense = mockk()
    val navigationRouter: NavigationRouter = mockk(relaxed = true)
    val currencyFormatter: CurrencyFormatter = mockk()
    val deleteExpense: DeleteExpense = mockk()

    fun tested(
        savedStateHandle: SavedStateHandle = noOp(),
        getExpensesTitles: GetExpensesTitles = noOp(),
        getExpensesPlaces: GetExpensesPlaces = noOp(),
        getCategories: GetCategories = noOp(),
        getExpense: GetExpense = noOp(),
        insertExpense: InsertExpense = noOp(),
        updateExpense: UpdateExpense = noOp(),
        navigationRouter: NavigationRouter = noOp(),
        currencyFormatter: CurrencyFormatter = noOp(),
        deleteExpense: DeleteExpense = noOp(),
    ): ExpenseFormViewModel =
        ExpenseFormViewModelImpl(
            savedStateHandle = savedStateHandle,
            getExpensesTitles = getExpensesTitles,
            getExpensesPlaces = getExpensesPlaces,
            getCategories = getCategories,
            getExpense = getExpense,
            insertExpense = insertExpense,
            updateExpense = updateExpense,
            dispatcherProvider = UnconfinedDispatcherProvider,
            navigationRouter = navigationRouter,
            currencyFormatter = currencyFormatter,
            deleteExpense = deleteExpense,
        )

    Given("Expense id is not provided") {
        val titles: List<String> = dummy()
        val places: List<String> = dummy()
        val categoryId = Id.new()
        val categoryName = "categoryName"
        val secondCategoryId = Id.new()
        val secondCategoryName = "secondCategoryName"
        val category = Category(
            id = categoryId,
            name = categoryName,
        )
        val secondCategory = Category(
            id = secondCategoryId,
            name = secondCategoryName,
        )
        val categories: List<Category> = listOf(category, secondCategory)
        val viewState = ExpenseFormViewModel.ViewState(
            isLoading = false,
            previousTitles = titles,
            previousPlaceNames = places,
            categories = listOf(
                ExpenseFormViewModel.ViewState.Category(
                    id = categoryId,
                    name = categoryName,
                    isSelected = false,
                ),
                ExpenseFormViewModel.ViewState.Category(
                    id = secondCategoryId,
                    name = secondCategoryName,
                    isSelected = false,
                ),
            ),
            submitButtonText = R.string.add,
            shouldOpenKeyboard = true,
        )

        every { savedStateHandle.get<Int>("EXPENSE_ID") } returns null
        coEvery { getExpensesTitles() } returns titles
        coEvery { getExpensesPlaces() } returns places
        coEvery { getCategories() } returns categories

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
            val priceBigDecimal = price.toBigDecimal()

            every { currencyFormatter.format(price) } returns priceBigDecimal

            val actualViewState = tested(
                savedStateHandle = savedStateHandle,
                getExpensesTitles = getExpensesTitles,
                getExpensesPlaces = getExpensesPlaces,
                getCategories = getCategories,
                currencyFormatter = currencyFormatter,
            ).apply {
                onPriceChanged(price)
            }.viewState.value

            Then("Price will be changed") {
                val expectedViewState = viewState.copy(price = Price.of(priceBigDecimal))

                actualViewState shouldBe expectedViewState
            }
        }

        When("New date will be given") {
            val date = ExpenseDate.now()

            val actualViewState = tested(
                savedStateHandle = savedStateHandle,
                getExpensesTitles = getExpensesTitles,
                getExpensesPlaces = getExpensesPlaces,
                getCategories = getCategories,
            ).apply {
                onDateChanged(date)
            }.viewState.value

            Then("Date will be changed") {
                val expectedViewState = viewState.copy(date = date)

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
            row("", "", Price.ZERO.amount),
            row("title", "", Price.ZERO.amount),
            row("", "2.00, ", 2.00.toBigDecimal())
        ) { title, price, formattedPrice ->
            And("Data are not valid (title: $title, price: $price)") {
                When("Submit button is clicked") {
                    every { currencyFormatter.format(price) } returns formattedPrice

                    val routeActions = tested(
                        savedStateHandle = savedStateHandle,
                        getExpensesTitles = getExpensesTitles,
                        getExpensesPlaces = getExpensesPlaces,
                        getCategories = getCategories,
                        insertExpense = insertExpense,
                        currencyFormatter = currencyFormatter,
                    ).apply {
                        onTitleChanged(title)
                        onPriceChanged(price)
                        onSubmitButtonClicked()
                    }.routeActions.firstOrNull()

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
            val date = ExpenseDate.now()
            val placeName = "new placeName"
            val description = "new description"

            coEvery { insertExpense(any(), any(), any(), any(), any(), any(), any()) } returns Unit
            every { currencyFormatter.format(price) } returns price.toBigDecimal()

            When("Data are valid") {
                And("Submit button is clicked") {
                    tested(
                        savedStateHandle = savedStateHandle,
                        getExpensesTitles = getExpensesTitles,
                        getExpensesPlaces = getExpensesPlaces,
                        getCategories = getCategories,
                        insertExpense = insertExpense,
                        navigationRouter = navigationRouter,
                        currencyFormatter = currencyFormatter,
                    ).apply {
                        onTitleChanged(title)
                        onPriceChanged(price)
                        onDateChanged(date)
                        onCategoryChanged(secondCategoryId)
                        onPlaceNameChanged(placeName)
                        onDescriptionChanged(description)
                        onIncomeValueChanged()
                        onSubmitButtonClicked()
                    }

                    Then("New expense should be inserted") {
                        coVerify {
                            insertExpense(
                                title = title,
                                price = Price.of(price),
                                date = date,
                                place = placeName,
                                description = description,
                                category = secondCategory,
                                type = Expense.Type.Income,
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
        val categoryId = Id.new()
        val categoryName = "categoryName"
        val secondCategoryId = Id.new()
        val secondCategoryName = "secondCategoryName"
        val category = Category(
            id = categoryId,
            name = categoryName,
        )
        val secondCategory = Category(
            id = secondCategoryId,
            name = secondCategoryName,
        )
        val categories: List<Category> = listOf(category, secondCategory)
        val expenseIdUuid = UUID.randomUUID().toString()
        val expenseId = Id.from(expenseIdUuid)
        val title = "expenseTitle"
        val price = Price.of(324.0)
        val date = ExpenseDate.now()
        val description = "expenseDescription"
        val place = "expensePlace"
        val type = Expense.Type.Outgo
        val expense = Expense(
            id = expenseId,
            title = title,
            price = price,
            date = date,
            description = description,
            place = place,
            category = category,
            type = type,
        )
        val viewState = ExpenseFormViewModel.ViewState(
            title = title,
            price = price,
            chosenCategoryId = categoryId,
            date = date,
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
            isLoading = false,
            type = type,
            shouldShowDeleteButton = true,
        )

        every { savedStateHandle.get<String>("EXPENSE_ID") } returns expenseIdUuid
        coEvery { getExpensesTitles() } returns titles
        coEvery { getExpensesPlaces() } returns places
        coEvery { getCategories() } returns categories
        coEvery { getExpense(any()) } returns expense

        When("View model is initialized") {
            val actualViewState = tested(
                savedStateHandle = savedStateHandle,
                getExpensesTitles = getExpensesTitles,
                getExpensesPlaces = getExpensesPlaces,
                getCategories = getCategories,
                getExpense = getExpense,
            ).viewState.value

            Then("View state will be set up for updating expense") {
                coEvery { getExpense(expenseId) }

                actualViewState shouldBe viewState
            }
        }

        And("Data for updating expense are valid") {
            val newTitle = "New title"
            val newPrice = "50.00"
            val newDate = ExpenseDate.now()
            val newPlaceName = "new placeName"
            val newDescription = "new description"

            coEvery { updateExpense(any(), any(), any(), any(), any(), any(), any(), any()) } returns Unit
            every { currencyFormatter.format(newPrice) } returns newPrice.toBigDecimal()

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
                        currencyFormatter = currencyFormatter,
                    ).apply {
                        onTitleChanged(newTitle)
                        onPriceChanged(newPrice)
                        onDateChanged(newDate)
                        onCategoryChanged(secondCategoryId)
                        onPlaceNameChanged(newPlaceName)
                        onDescriptionChanged(newDescription)
                        onOutgoValueChanged()
                        onSubmitButtonClicked()
                    }

                    Then("New expense should be inserted") {
                        coVerify {
                            updateExpense(
                                id = expenseId,
                                title = newTitle,
                                price = Price.of(newPrice),
                                date = newDate,
                                place = newPlaceName,
                                description = newDescription,
                                category = secondCategory,
                                type = Expense.Type.Outgo,
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
