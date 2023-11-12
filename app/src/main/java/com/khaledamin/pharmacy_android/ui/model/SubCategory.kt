package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class SubCategory(
    @SerializedName("subcategoryId")
    val subCategoryId: Long?,
    @SerializedName("subcategoryTitle")
    val title: String?,
//    @SerializedName("products")
//    val products: List<Product>
)