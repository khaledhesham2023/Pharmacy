package com.khaledamin.pharmacy_android.ui.model.requests

import com.google.gson.annotations.SerializedName

data class AddAddressRequest(
    @SerializedName("area")
    val area: String?,
    @SerializedName("latitude")
    val latitude:Double?,
    @SerializedName("longitude")
    val longitude:Double?,
    @SerializedName("street")
    val street:String?,
    @SerializedName("blockNo")
    val blockNo:Int?,
    @SerializedName("floorNo")
    val floorNo:Int?,
    @SerializedName("apartmentNo")
    val apartmentNo:Int?,
    @SerializedName("additionalDetails")
    val additionalDetails:String?,
    @SerializedName("city")
    val city:String?,
    @SerializedName("governorate")
    val governorate:String?,
    @SerializedName("userId")
    val userId:Long?,
    @SerializedName("addressName")
    val addressName:String?,
    @SerializedName("phone")
    val phone:String?,
    @SerializedName("isDefault")
    val isDefault:Boolean
    )