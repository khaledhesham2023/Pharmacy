package com.khaledamin.pharmacy_android.ui.product

import com.khaledamin.pharmacy_android.ui.model.Product

interface ProductCallback {
    fun onProductClicked(product: Product)
    fun onProductAdded(product: Product)
    fun onPlusClicked(product: Product,quantity: Int)
    fun onMinusClicked(product: Product,quantity: Int)
    fun onProductRemoved(product: Product)
    fun onProductLikeClicked(product: Product)
    fun onProductDislikeClicked(product: Product)
}