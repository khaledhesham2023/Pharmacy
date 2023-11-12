package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class OrderStatus(
    @SerializedName("statusId")
    var statusId:Long?,
    @SerializedName("statusName")
    var statusName:String?
)