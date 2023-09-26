package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class SubCategory(
    @SerializedName("id")
    val subCategoryId: Long?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("products")
    val products: List<Product>
)