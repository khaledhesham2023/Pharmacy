package com.khaledamin.pharmacy_android.ui.model.responses

import com.google.gson.annotations.SerializedName

data class SendOTPResponse(
    @SerializedName("message")
    val message:String?,
    @SerializedName("status")
    val status:Boolean,
    @SerializedName("otp")
    val otp:String?
)