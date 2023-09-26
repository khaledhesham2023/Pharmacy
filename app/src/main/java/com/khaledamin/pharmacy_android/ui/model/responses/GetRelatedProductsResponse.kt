package com.khaledamin.pharmacy_android.ui.model.responses

import com.google.gson.annotations.SerializedName
import com.khaledamin.pharmacy_android.ui.model.Product

data class GetRelatedProductsResponse(
    @SerializedName("products")
    val products:List<Product>
)