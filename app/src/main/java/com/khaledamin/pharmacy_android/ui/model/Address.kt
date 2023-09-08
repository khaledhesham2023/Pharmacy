package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("addressId")
    val addressId: Long?,
    @SerializedName("area")
    val area: String?,
    @SerializedName("street")
    val street: String?,
    @SerializedName("blockNo")
    val blockNo: Int?,
    @SerializedName("floorNo")
    val floorNo: Int?,
    @SerializedName("apartmentNo")
    val apartmentNo: Int?,
    @SerializedName("additionalDetails")
    val additionalDetails: String?,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?,
    @SerializedName("governorate")
    val governorate: String?,
    @SerializedName("city")
    val city:String?,
    @SerializedName("isDefault")
    val isDefault:Boolean?,
    @SerializedName("phone")
    val phone:String?,
    @SerializedName("addressName")
    val addressName:String?
    )