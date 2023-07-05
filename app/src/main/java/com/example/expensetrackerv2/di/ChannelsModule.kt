package com.example.expensetrackerv2.di

import com.github.pploszczyca.expensetrackerv2.features.main.features.bottom_bar.MainBottomBarEvent
import com.github.pploszczyca.expensetrackerv2.features.main.features.filter_dialog.MainFilterDialogEvent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.Channel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChannelsModule {
    @Provides
    @Singleton
    fun provideMainBottomBarChannel(): Channel<com.github.pploszczyca.expensetrackerv2.features.main.features.bottom_bar.MainBottomBarEvent> = Channel()

    @Provides
    @Singleton
    fun provideMainFilterDialogChannel(): Channel<com.github.pploszczyca.expensetrackerv2.features.main.features.filter_dialog.MainFilterDialogEvent> = Channel()
}
