package com.khaledamin.pharmacy_android.ui.addresses

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentAddressesBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.ui.model.Address
import com.khaledamin.pharmacy_android.ui.model.requests.SetDefaultAddressRequest
import com.khaledamin.pharmacy_android.utils.DisplayManager.showAlertDialog
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressesFragment :
    BaseFragmentWithViewModel<FragmentAddressesBinding, AddressesViewModel>(), AddressesCallback {

    private lateinit var addressesAdapter: AddressesAdapter

    override val viewModelClass: Class<AddressesViewModel>
        get() = AddressesViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserAddresses(viewModel.getUser()!!.id!!)
        addressesAdapter = AddressesAdapter(ArrayList(), this)
        viewBinding.addressesList.adapter = addressesAdapter
        viewBinding.addressesList.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun setupListeners() {
        viewBinding.addAddressBtn.setOnClickListener {
            findNavController().navigate(
                AddressesFragmentDirections.actionAddressesFragmentToAddAddressFragment(
                    0.0f,
                    0.0f,
                    ""
                )
            )
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
        viewModel.setDefaultAddressLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                    viewModel.getUserAddresses(viewModel.getUser()!!.id!!)
                    loadingDialog.dismiss()
                }

                is ViewState.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.removeAddressLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                    viewModel.getUserAddresses(viewModel.getUser()!!.id!!)
                    loadingDialog.dismiss()
                }

                is ViewState.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                }
            }
        })
    }

    override val layout: Int
        get() = R.layout.fragment_addresses

    override fun onAddressSettingsClicked(view: View, address: Address) {
        showPopUpMenu(view, address)
    }

    private fun showPopUpMenu(view: View, address: Address) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.addresses_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item!!.itemId) {
                R.id.set_default_address -> {
                    if (address.isDefault == false) {
                        showAlertDialog(
                            requireContext(),
                            R.string.confirmation,
                            R.string.confirm_set_default_address,
                            R.string.confirm,
                            R.string.cancel
                        ) { _, _ ->
                            viewModel.setDefaultAddress(
                                address.addressId!!,
                                SetDefaultAddressRequest(viewModel.getUser()!!.id)
                            )
                        }
                    } else {
                        showAlertDialog(
                            requireContext(),
                            R.string.warning,
                            R.string.set_default_address_error,
                            R.string.close,
                            R.string.cancel
                        ) { _, _ ->
                        }
                    }

                }

                R.id.remove_address -> {
                    if (address.isDefault == false) {
                        showAlertDialog(
                            requireContext(),
                            R.string.confirmation,
                            R.string.confirm_address_remove,
                            R.string.confirm,
                            R.string.cancel
                        ) { _, _ ->
                            viewModel.removeAddress(
                                address.addressId!!,
                                SetDefaultAddressRequest(viewModel.getUser()!!.id)
                            )
                        }
                    } else {
                        showAlertDialog(
                            requireContext(),
                            R.string.warning,
                            R.string.default_address_error,
                            R.string.close,
                            R.string.cancel
                        ) { _, _ ->
                        }
                    }
                }
            }
            true
        }
        popupMenu.show()
    }
}