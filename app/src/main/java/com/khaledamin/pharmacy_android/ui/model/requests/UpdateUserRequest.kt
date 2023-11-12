package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class UpdateUserRequest(
    @SerializedName("userId")
    val userId:Long?,
    @SerializedName("firstname")
    val firstname:String?,
    @SerializedName("lastname")
    val lastname:String?,
    @SerializedName("username")
    val username:String?,
    @SerializedName("email")
    val email:String?,
    @SerializedName("phone")
    val phone:String?,
    @SerializedName("password")
    val password:String?
)