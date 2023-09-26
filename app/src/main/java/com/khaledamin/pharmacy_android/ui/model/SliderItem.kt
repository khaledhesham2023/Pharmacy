package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class SliderItem(
    @SerializedName("id")
    val sliderId:Long?,
    @SerializedName("sliderTitle")
    val sliderTitle:String?,
    @SerializedName("sliderImage")
    val sliderImage:String?
)