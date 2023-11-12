package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("oldPassword")
    val oldPassword:String?,
    @SerializedName("newPassword")
    val newPassword:String?,
    @SerializedName("confirmNewPassword")
    val confirmNewPassword:String?,
    @SerializedName("userId")
    val userId:Long?
)