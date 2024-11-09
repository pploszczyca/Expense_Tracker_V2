package com.example.expensetrackerv2.di

import com.github.pploszczyca.expensetrackerv2.telemetry.spans.DefaultSpanFactory
import com.github.pploszczyca.expensetrackerv2.telemetry.spans.SpanFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TelemetryModule {
    @Provides
    fun provideSpanContext(): SpanFactory = DefaultSpanFactory()
}