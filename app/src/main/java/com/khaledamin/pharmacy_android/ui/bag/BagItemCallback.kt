package com.khaledamin.pharmacy_android.ui.bag

import com.khaledamin.pharmacy_android.ui.model.CartItem
import com.khaledamin.pharmacy_android.ui.model.Product

interface BagItemCallback {

    fun onPlusClicked(quantity:Int, cartItem: CartItem)

    fun onMinusClicked(quantity: Int,cartItem:CartItem)

    fun onProductRemoved(cartItem: CartItem)
}