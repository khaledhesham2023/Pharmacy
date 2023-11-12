package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class OrderItem(
    @SerializedName("orderItemId")
    var orderItemId:Long?,
    @SerializedName("product")
    var product: Product?,
    @SerializedName("quantity")
    var quantity:Int?
)