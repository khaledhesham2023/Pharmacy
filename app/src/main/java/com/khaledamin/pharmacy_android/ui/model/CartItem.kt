package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class CartItem(
    @SerializedName("cartId")
    var cartItemId:Long?,
    @SerializedName("product")
    var product: Product?,
    @SerializedName("quantity")
    var quantity:Int?
)