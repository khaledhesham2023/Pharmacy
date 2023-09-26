package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class GetRelatedProductsRequest(
    @SerializedName("id")
    val id:Long
)