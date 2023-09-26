package com.khaledamin.pharmacy_android.ui.addresses

import com.khaledamin.pharmacy_android.ui.model.AddressType

interface AddressTypeCallback {
    fun onAddressTypeSelected(type: AddressType)
}