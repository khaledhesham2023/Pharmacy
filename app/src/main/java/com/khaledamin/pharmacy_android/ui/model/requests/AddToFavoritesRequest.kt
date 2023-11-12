package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class AddToFavoritesRequest(
    @SerializedName("userId")
    val userId:Long?,
    @SerializedName("productId")
    val productId:Long?
)