package com.khaledamin.pharmacy_android.ui.orders

import com.khaledamin.pharmacy_android.ui.model.Order

interface CurrentOrderCallback {

    fun onCurrentOrderDetailsClicked(order : Order)
}