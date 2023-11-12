package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class FilterRequest(
    @SerializedName("subcategoryId")
    val subcategoryId:Long?,
    @SerializedName("pageNo")
    val pageNo:Int?
)