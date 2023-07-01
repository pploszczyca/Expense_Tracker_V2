package com.example.expensetrackerv2.di

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.example.expensetrackerv2.navigation.NavigationRouterImpl
import com.github.pploszczyca.expensetrackerb2.navigation.contract.NavigationRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    fun provideNavHostController(): MutableState<NavHostController?> =
        mutableStateOf(null)

    @Provides
    fun provideNavigationRouter(navHostController: MutableState<NavHostController?>): NavigationRouter =
        navHostController.value
            .let(::requireNotNull)
            .let(::NavigationRouterImpl)
}

