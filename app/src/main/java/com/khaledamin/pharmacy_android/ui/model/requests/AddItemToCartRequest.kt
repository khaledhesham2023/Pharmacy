package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class AddItemToCartRequest(
    @SerializedName("userId")
    val userId:Long,
    @SerializedName("productId")
    val productId:Long?,
    @SerializedName("quantity")
    val quantity:Int?
)