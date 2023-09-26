package com.khaledamin.pharmacy_android.ui.product

import com.khaledamin.pharmacy_android.ui.model.SubCategory

interface SubcategoryCallback {

    fun onSubcategoryClicked(subCategory: SubCategory, position: Int)
}