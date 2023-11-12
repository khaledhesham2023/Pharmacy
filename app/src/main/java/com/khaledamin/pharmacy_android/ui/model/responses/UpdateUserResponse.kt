package com.khaledamin.pharmacy_android.ui.model.responses

import com.google.gson.annotations.SerializedName
import com.khaledamin.pharmacy_android.ui.model.UpdatedUserData

data class UpdateUserResponse(
    @SerializedName("response")
    val response: BaseResponse,
    @SerializedName("user")
    val user: UpdatedUserData
)