package com.khaledamin.pharmacy_android.ui.bag

import com.khaledamin.pharmacy_android.ui.model.Payment
import com.khaledamin.pharmacy_android.ui.model.Shipping

interface ShippingCallback {

    fun onShippingMethodSelected(shipping: Shipping)
}