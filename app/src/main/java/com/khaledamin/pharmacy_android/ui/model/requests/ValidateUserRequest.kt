package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class ValidateUserRequest(
    @SerializedName("otp")
    val otp:String?,
    @SerializedName("phone")
    val phone:String?
)