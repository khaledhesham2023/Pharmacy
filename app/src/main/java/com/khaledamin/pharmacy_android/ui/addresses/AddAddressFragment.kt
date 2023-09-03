package com.khaledamin.pharmacy_android.ui.addresses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentAddAddressBinding
import com.khaledamin.pharmacy_android.databinding.FragmentAddressesBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAddressFragment :
    BaseFragmentWithViewModel<FragmentAddAddressBinding, AddAddressViewModel>() {
    override val viewModelClass: Class<AddAddressViewModel>
        get() = AddAddressViewModel::class.java

    override fun setupListeners() {
        viewBinding.selectAddressMapBtn.setOnClickListener {
            findNavController().navigate(AddAddressFragmentDirections.actionAddAddressFragmentToMapsFragment())
        }
    }

    override fun setupObservers() {
//        TODO("Not yet implemented")
    }

    override val layout: Int
        get() = R.layout.fragment_add_address

}