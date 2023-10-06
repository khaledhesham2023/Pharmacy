package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class Shipping(
    @SerializedName("shippingId")
    val shippingId:Long?,
    @SerializedName("shippingName")
    val shippingName:String?,
    @SerializedName("shippingAmount")
    val shippingAmount:Double?
)