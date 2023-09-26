package com.khaledamin.pharmacy_android.ui.product

import com.khaledamin.pharmacy_android.ui.model.Product

interface ProductCallback {
    fun onProductClicked(product:Product)
}