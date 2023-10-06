package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class CreateOrderRequest(
    @SerializedName("userId")
    val userId: Long?,
    @SerializedName("shippingId")
    val shippingId: Long?,
    @SerializedName("paymentId")
    val paymentId:Long?
)