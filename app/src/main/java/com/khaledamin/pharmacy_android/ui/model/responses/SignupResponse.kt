package com.khaledamin.pharmacy_android.ui.model.responses

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("message")
    val message:String?,
    @SerializedName("status")
    val status:Boolean,
    @SerializedName("email")
    val email:String?,
    @SerializedName("username")
    val username:String?
)