package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName
import com.khaledamin.pharmacy_android.utils.Selectable

data class CategoryItem(
    @SerializedName("id")
    val id:Long,
    @SerializedName("name")
    val name:String,
    @SerializedName("subCategories")
    val subCategories:List<SubCategory>
):Selectable {
    override var isSelected: Boolean = false
}