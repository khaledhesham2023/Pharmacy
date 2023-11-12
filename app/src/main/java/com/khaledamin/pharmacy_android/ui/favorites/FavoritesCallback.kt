package com.khaledamin.pharmacy_android.ui.favorites

import com.khaledamin.pharmacy_android.ui.model.Product

interface FavoritesCallback {

    fun onFavoriteProductClicked(product: Product)

    fun onLikeIconClicked(product: Product)
}