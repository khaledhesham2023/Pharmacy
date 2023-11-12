package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("notificationId")
    val notificationId:Long?,
    @SerializedName("notificationIcon")
    val notificationIcon:String?,
    @SerializedName("notificationTitle")
    val notificationTitle:String?,
    @SerializedName("notificationTime")
    val notificationTime: String?
)