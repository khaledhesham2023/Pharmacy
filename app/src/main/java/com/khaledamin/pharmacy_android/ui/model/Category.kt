package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category(
    @SerializedName("id")
    var categoryId:Long?,
    @SerializedName("categoryTitle")
    val categoryTitle:String?,
    @SerializedName("categoryImage")
    val categoryImage: String?
):Serializable