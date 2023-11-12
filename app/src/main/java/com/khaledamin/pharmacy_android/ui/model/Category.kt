package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName
import com.khaledamin.pharmacy_android.utils.Selectable
import java.io.Serializable

data class Category(
    @SerializedName("categoryId")
    var categoryId:Long?,
    @SerializedName("categoryName")
    var categoryName:String?,
    @SerializedName("categoryImage")
    var categoryImage: String?
):Serializable, Selectable {
    override var isSelected: Boolean = false
}