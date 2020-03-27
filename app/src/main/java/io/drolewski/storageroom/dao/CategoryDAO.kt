package io.drolewski.storageroom.dao

import androidx.room.*
import io.drolewski.storageroom.entity.Category
import io.drolewski.storageroom.entity.CategoryWithObject

@Dao
interface CategoryDAO {
    @Insert(entity = Category::class)
    fun insertCategory(vararg category: Category)

    @Update(entity = Category::class)
    fun updateCategory(vararg category: Category)

    @Delete(entity = Category::class)
    fun deleteCategory(vararg category: Category)

    @Query("SELECT * FROM category where category_name LIKE :word")
    fun findCategory(word: String): List<Category>

    @Transaction
    @Query("SELECT * FROM Category")
    fun getCategoryWithObject(): List<CategoryWithObject>

    @Transaction
    @Query("SELECT * FROM Category")
    fun getPlainCategory(): List<Category>
}