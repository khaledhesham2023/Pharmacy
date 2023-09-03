package com.khaledamin.pharmacy_android.ui.model.responses

import com.google.gson.annotations.SerializedName
import com.khaledamin.pharmacy_android.ui.model.Category
import com.khaledamin.pharmacy_android.ui.model.SliderItem

data class GetCatalogResponse(
    @SerializedName("sliders")
    val sliders:ArrayList<SliderItem>?,
    @SerializedName("categories")
    val categories:ArrayList<Category>?
)