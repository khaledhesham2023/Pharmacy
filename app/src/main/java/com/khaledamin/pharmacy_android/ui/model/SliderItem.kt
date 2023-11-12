package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class SliderItem(
    @SerializedName("sliderId")
    var sliderId:Long?,
    @SerializedName("sliderTitle")
    val sliderTitle:String?,
    @SerializedName("sliderImage")
    val sliderImage:String?,
    @SerializedName("product")
    val product:Product?
)