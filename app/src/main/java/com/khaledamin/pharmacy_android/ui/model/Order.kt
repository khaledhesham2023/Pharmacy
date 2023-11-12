package com.khaledamin.pharmacy_android.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serial
import java.io.Serializable

data class Order(
    @SerializedName("orderId")
    val id: Long?,
    @SerializedName("dateCreated")
    val dateCreated: String?,
    @SerializedName("subtotal")
    val subtotal: Double?,
    @SerializedName("shipping")
    val shipping: Shipping?,
    @SerializedName("total")
    val total: Double?,
    @SerializedName("address")
    val address: Address?,
    @SerializedName("discount")
    val discount: Double?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("incrementId")
    val incrementId: String?,
    @SerializedName("products")
    val products: List<OrderItem>?,
    @SerializedName("payment")
    val payment: Payment?,
    @SerializedName("orderStatus")
    val status: OrderStatus?,
) : Serializable