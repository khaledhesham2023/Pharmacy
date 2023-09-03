package com.khaledamin.pharmacy_android.ui.addresses

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentAddressesBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressesFragment :
    BaseFragmentWithViewModel<FragmentAddressesBinding, AddressesViewModel>() {

    private lateinit var addressesAdapter: AddressesAdapter

    override val viewModelClass: Class<AddressesViewModel>
        get() = AddressesViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserAddresses(viewModel.getUser()!!.id!!)
        addressesAdapter = AddressesAdapter(ArrayList())
        viewBinding.addressesList.adapter = addressesAdapter
        viewBinding.addressesList.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun setupListeners() {
        viewBinding.addAddressBtn.setOnClickListener {
            findNavController().navigate(AddressesFragmentDirections.actionAddressesFragmentToAddAddressFragment())
        }
    }

    override fun setupObservers() {
        viewModel.userAddressesLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    addressesAdapter.updateDataSet(it.data.data)
                    loadingDialog.dismiss()
                }

                is ViewState.Error -> {
                    showErrorAlertDialog(
                        requireContext(),
                        R.string.error,
                        it.message,
                        R.string.retry,
                        R.string.cancel
                    ) { _, _ ->
                        viewModel.getUserAddresses(viewModel.getUser()!!.id!!)
                    }
                    loadingDialog.dismiss()
                }
            }
        })
    }

    override val layout: Int
        get() = R.layout.fragment_addresses


}