package com.khaledamin.pharmacy_android.ui.model.responses

import com.google.gson.annotations.SerializedName
import com.khaledamin.pharmacy_android.ui.model.User

data class LoginResponse(
    @SerializedName("status")
    val status:Boolean,
    @SerializedName("message")
    val message:String?,
    @SerializedName("userData")
    val user: User?
)