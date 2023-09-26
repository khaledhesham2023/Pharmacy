package com.khaledamin.pharmacy_android.ui.product

import com.khaledamin.pharmacy_android.ui.model.CategoryItem

interface CategoryItemCallback {
    fun onCategoryItemClicked(categoryItem: CategoryItem,position:Int)
}