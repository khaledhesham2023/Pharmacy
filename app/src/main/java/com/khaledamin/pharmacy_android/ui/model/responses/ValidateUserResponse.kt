package com.khaledamin.pharmacy_android.ui.model.responses

import com.google.gson.annotations.SerializedName

data class ValidateUserResponse(
    @SerializedName("status")
    val status:Boolean,
    @SerializedName("message")
    val message:String?
)