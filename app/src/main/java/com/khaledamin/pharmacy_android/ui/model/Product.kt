package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
    @SerializedName("productId")
    val productId:Long?,
    @SerializedName("productName")
    val name:String?,
    @SerializedName("productImage")
    val image:String?,
    @SerializedName("productBrand")
    val brand:String?,
    @SerializedName("productUnitPrice")
    val unitPrice:Double?,
    @SerializedName("productPackPrice")
    val packPrice:Double?,
    @SerializedName("productUnit")
    val unit:String?,
    @SerializedName("productWeight")
    val weight:Double?,
    @SerializedName("productRate")
    val rate:Double?,
    @SerializedName("productDetails")
    val details:String?,
    @SerializedName("productActivePrincipal")
    val activePrincipal:String?,
    @SerializedName("manufacturer")
    val manufacturer:String?,
    @SerializedName("quantity")
    val quantity:Int?
):Serializable