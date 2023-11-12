package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class ProductsRequest(
    @SerializedName("categoryId")
    var categoryId:Long?,
    @SerializedName("pageNo")
    var pageNo:Int?
)