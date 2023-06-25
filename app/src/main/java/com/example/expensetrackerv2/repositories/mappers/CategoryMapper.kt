package com.example.expensetrackerv2.repositories.mappers

import com.example.expensetrackerv2.models.CategoryEntity
import com.example.expensetrackerv2.models.CategoryType
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