package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("firstname")
    val firstname:String?,
    @SerializedName("lastname")
    val lastname:String?,
    @SerializedName("password")
    val password:String?,
    @SerializedName("username")
    val username:String?,
    @SerializedName("phone")
    val phone:String?,
    @SerializedName("email")
    val email:String?
)
