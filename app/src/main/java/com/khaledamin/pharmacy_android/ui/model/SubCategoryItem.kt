package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class SubCategoryItem(
    @SerializedName("subcategoryId")
    var subcategoryId: Long?,
    @SerializedName("subcategoryTitle")
    var subcategoryTitle:String?,
    @SerializedName("category")
    var category: Category
) {
}