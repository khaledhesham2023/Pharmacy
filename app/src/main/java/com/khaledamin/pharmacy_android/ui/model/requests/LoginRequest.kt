package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val username:String?,
    @SerializedName("password")
    val password:String?
)