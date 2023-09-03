package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("categoryId")
    val categoryId:Long?,
    @SerializedName("categoryTitle")
    val categoryTitle:String?,
    @SerializedName("categoryImage")
    val categoryImage: String?
)