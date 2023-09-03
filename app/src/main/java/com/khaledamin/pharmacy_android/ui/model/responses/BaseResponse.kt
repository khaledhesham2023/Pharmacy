package com.khaledamin.pharmacy_android.ui.model.responses

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("status")
    val status:Boolean,
    @SerializedName("message")
    val message:String?
)