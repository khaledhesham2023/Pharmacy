package com.khaledamin.pharmacy_android.ui.categories

import com.khaledamin.pharmacy_android.ui.model.Category

interface CategoryCallback {

    fun onCategoryClicked(category: Category, position:Int)
}