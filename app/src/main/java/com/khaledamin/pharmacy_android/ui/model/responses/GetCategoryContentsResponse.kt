package com.khaledamin.pharmacy_android.ui.model.responses

import com.google.gson.annotations.SerializedName
import com.khaledamin.pharmacy_android.ui.model.CategoryItem

data class GetCategoryContentsResponse(
    @SerializedName("categories")
    val categories:List<CategoryItem>
)