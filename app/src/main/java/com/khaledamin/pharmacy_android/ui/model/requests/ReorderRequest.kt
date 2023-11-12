package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class ReorderRequest(
    @SerializedName("orderId")
    var orderId:Long?,
    @SerializedName("userId")
    var userId:Long?
)