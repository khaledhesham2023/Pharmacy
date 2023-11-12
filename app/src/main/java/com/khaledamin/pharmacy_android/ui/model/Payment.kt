package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("paymentId")
    val paymentId:Long?,
    @SerializedName("paymentName")
    val paymentName:String?,
    @SerializedName("paymentIcon")
    val paymentIcon:String?
)
