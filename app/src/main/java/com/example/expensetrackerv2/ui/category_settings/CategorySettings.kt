package com.example.expensetrackerv2.ui.category_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.ui.common_components.bar.TopAppBarWithBack
import com.github.pploszczyca.expensetrackerv2.domain.Category

@Composable
fun CategorySettings(
    navController: NavController,
    viewModel: CategorySettingsViewModel,
) {
    val categories by viewModel.categories.collectAsState(initial = emptyList())

    val isDialogFormVisible by viewModel.isDialogFormVisible
    val isDeleteDialogFormVisible by viewModel.isDeleteDialogFormVisible

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(id = R.string.drawer_type_of_expense_settings),
                onBackClicked = navController::navigateUp,
            )
        },

        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onEvent(CategorySettingsEvent.OpenFormDialog()) }) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.add_icon))
            }
        },

        content = { paddingValues ->
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(categories) { categoryItem: Category ->
                    CategoryCard(
                        category = categoryItem,
                        onUpdateButtonClick = {
                            it.let(CategorySettingsEvent::OpenFormDialog).let(viewModel::onEvent)
                        },
                        onDeleteButtonClick = {
                            it.let(CategorySettingsEvent::OpenDeleteDialog).let(viewModel::onEvent)
                        })
                }
            }

            if (isDialogFormVisible) {
                CategoryDialogForm(modelView = viewModel)
            }

            if (isDeleteDialogFormVisible) {
                CategoryDeleteDialog(modelView = viewModel)
            }
        })
}