package com.khaledamin.pharmacy_android.ui.bag

import com.khaledamin.pharmacy_android.ui.model.Payment

interface PaymentCallback {

    fun onPaymentTypeSelected(payment: Payment)
}