package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class UpdatedUserData(
    @SerializedName("id")
    val id:Long?,
    @SerializedName("firstname")
    val firstname:String?,
    @SerializedName("lastname")
    val lastname:String?,
    @SerializedName("username")
    val username:String?,
    @SerializedName("phone")
    val phone:String?,
    @SerializedName("email")
    val email:String?,
    @SerializedName("code")
    val code:String?
)