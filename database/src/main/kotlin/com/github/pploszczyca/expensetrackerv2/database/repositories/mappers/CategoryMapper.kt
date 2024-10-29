package com.github.pploszczyca.expensetrackerv2.database.repositories.mappers

import com.github.pploszczyca.expensetrackerv2.database.models.CategoryEntity
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Id

internal class CategoryMapper {
    fun toDomainModel(categoryEntity: CategoryEntity): Category =
        Category(
            id = categoryEntity.id.let(Id::from),
            name = categoryEntity.name,
        )

    fun toDatabaseModel(category: Category): CategoryEntity =
        CategoryEntity(
            id = category.id.toString(),
            name = category.name,
        )
}