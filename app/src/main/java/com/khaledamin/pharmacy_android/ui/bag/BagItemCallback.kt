package com.khaledamin.pharmacy_android.ui.bag

import com.khaledamin.pharmacy_android.ui.model.Product

interface BagItemCallback {

    fun onPlusClicked(quantity:Int, product: Product)

    fun onMinusClicked(quantity: Int,product: Product)

    fun onProductRemoved(product: Product)
}