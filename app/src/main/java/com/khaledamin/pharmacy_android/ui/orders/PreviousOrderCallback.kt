package com.khaledamin.pharmacy_android.ui.orders

import com.khaledamin.pharmacy_android.ui.model.Order

interface PreviousOrderCallback {

    fun onPreviousOrderDetailsClicked(order: Order)

    fun onPreviousOrderReorderClicked(order: Order)

}