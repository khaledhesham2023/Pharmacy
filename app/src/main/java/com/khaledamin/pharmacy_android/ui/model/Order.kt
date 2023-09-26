package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serial
import java.io.Serializable

data class Order(
    @SerializedName("id")
    val id:Long?,
    @SerializedName("dateCreated")
    val dateCreated:String?,
    @SerializedName("subtotal")
    val subtotal:Double?,
    @SerializedName("shipping")
    val shipping:Double?,
    @SerializedName("total")
    val total:Double?,
    @SerializedName("address")
    val address: Address?,
    @SerializedName("discount")
    val discount:Double?,
    @SerializedName("email")
    val email:String?,
    @SerializedName("firstname")
    val firstname:String?,
    @SerializedName("lastname")
    val lastname:String?,
    @SerializedName("incrementId")
    val incrementId:String?,
    @SerializedName("products")
    val products: List<Product>?,
    @SerializedName("payment")
    val payment:String?,
    @SerializedName("status")
    val status:String?
):Serializable