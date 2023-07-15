package com.example.expensetrackerv2.di

import android.content.ContentResolver
import android.content.Context
import com.github.pploszczyca.expensetrackerv2.database.di.DatabaseDI
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContentResolver(@ApplicationContext appContext: Context): ContentResolver =
        appContext.contentResolver

    @Provides
    fun defaultDispatcher(): CoroutineDispatcher =
        Dispatchers.Default
}