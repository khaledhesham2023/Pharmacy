package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class SendOTPRequest(
    @SerializedName("number")
    val number:String?,
)