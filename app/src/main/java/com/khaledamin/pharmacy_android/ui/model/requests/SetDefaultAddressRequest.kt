package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class SetDefaultAddressRequest(
    @SerializedName("userId")
    val userId:Long?
)