package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("phoneNumber")
    val phoneNumber:String?,
    @SerializedName("password")
    val password:String?
)