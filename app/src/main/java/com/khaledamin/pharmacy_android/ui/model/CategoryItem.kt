package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class CategoryItem(
    @SerializedName("id")
    val id:Long,
    @SerializedName("name")
    val name:String,
    @SerializedName("subCategories")
    val subCategories:List<SubCategory>
)