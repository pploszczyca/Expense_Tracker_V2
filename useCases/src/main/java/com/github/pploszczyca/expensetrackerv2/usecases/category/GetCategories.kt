package com.github.pploszczyca.expensetrackerv2.usecases.category

import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.telemetry.spans.SpanContext
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.CategoryRepository

class GetCategories(
    private val repository: CategoryRepository,
) {
    context(SpanContext)
    suspend operator fun invoke(): List<Category> =
        inSpanSuspend("GetCategories") {
            repository.getAll()
        }
}