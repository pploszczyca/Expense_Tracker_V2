package com.github.pploszczyca.expensetrackerv2.database.repositories.mappers

import com.github.pploszczyca.expensetrackerv2.database.models.CategoryEntity
import com.github.pploszczyca.expensetrackerv2.database.models.CategoryType
import com.github.pploszczyca.expensetrackerv2.domain.Category

class CategoryMapper {
    fun toDomainModel(categoryEntity: CategoryEntity): Category =
        Category(
            id = categoryEntity.id,
            name = categoryEntity.name,
            type = when (categoryEntity.categoryType) {
                CategoryType.INCOME -> Category.Type.INCOME
                CategoryType.OUTGO -> Category.Type.OUTGO
            },
        )

    fun toDatabaseModel(category: Category): CategoryEntity =
        CategoryEntity(
            id = category.id,
            name = category.name,
            categoryType = when (category.type) {
                Category.Type.INCOME -> CategoryType.INCOME
                Category.Type.OUTGO -> CategoryType.OUTGO
            }
        )
}