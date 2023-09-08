package com.khaledamin.pharmacy_android.ui.addresses

import android.view.View
import com.khaledamin.pharmacy_android.ui.model.Address

interface AddressesCallback {

    fun onAddressSettingsClicked(view: View, address: Address)
}