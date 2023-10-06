package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName

data class AddressType(
    @SerializedName("typeId")
    val typeId:Long?,
    @SerializedName("typeName")
    val typeName:String?,
    @SerializedName("typeNameAr")
    val typeNameAr:String?
)