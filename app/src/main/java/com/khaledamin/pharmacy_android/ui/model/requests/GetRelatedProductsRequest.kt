package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class GetRelatedProductsRequest(
    @SerializedName("page")
    val page:Int?,
    @SerializedName("productId")
    val productId:Long?
)