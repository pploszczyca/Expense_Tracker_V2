package com.example.expensetrackerv2

import com.example.expensetrackerv2.ui.main.features.bottom_bar.MainBottomBarEvent
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
    fun provideMainBottomBarChannel(): Channel<MainBottomBarEvent> = Channel()
}