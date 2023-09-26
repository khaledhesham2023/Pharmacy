package com.khaledamin.pharmacy_android.ui.model.responses

import com.google.gson.annotations.SerializedName
import com.khaledamin.pharmacy_android.ui.model.Order

data class ReorderResponse(
    @SerializedName("response")
    val response: BaseResponse,
    @SerializedName("order")
    val order:Order
)