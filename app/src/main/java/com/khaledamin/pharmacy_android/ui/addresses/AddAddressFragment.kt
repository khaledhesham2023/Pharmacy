package com.khaledamin.pharmacy_android.ui.addresses

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentAddAddressBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.ui.model.requests.AddAddressRequest
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAddressFragment :
    BaseFragmentWithViewModel<FragmentAddAddressBinding, AddAddressViewModel>() {

    private var latitude: Float = 0.0f
    private lateinit var addressName: String
    private var longitude: Float = 0.0f
    private var isDefault = false


    override val viewModelClass: Class<AddAddressViewModel>
        get() = AddAddressViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        latitude = AddAddressFragmentArgs.fromBundle(requireArguments()).latitude ?: 0.0f
        longitude = AddAddressFragmentArgs.fromBundle(requireArguments()).longitude ?: 0.0f
        addressName = AddAddressFragmentArgs.fromBundle(requireArguments()).addressName ?: ""
        viewBinding.customerPhone.setText(viewModel.getPhone())
        viewBinding.addressName.setText(addressName) ?: ""
    }

    override fun setupListeners() {
        viewBinding.selectAddressMapBtn.setOnClickListener {
            findNavController().navigate(AddAddressFragmentDirections.actionAddAddressFragmentToMapsFragment())
        }
        viewBinding.cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
        viewBinding.addButton.setOnClickListener {
            if (isDataOk()) {
                viewModel.addUserAddress(
                    AddAddressRequest(
                        viewBinding.area.text.toString().trim(),
                        latitude.toDouble(),
                        longitude.toDouble(),
                        viewBinding.street.text.toString().trim(),
                        Integer.parseInt(viewBinding.building.text.toString().trim()),
                        Integer.parseInt(viewBinding.floor.text.toString().trim()),
                        Integer.parseInt(viewBinding.apartment.text.toString().trim()),
                        viewBinding.info.text.toString().trim(),
                        viewBinding.city.text.toString().trim(),
                        viewBinding.governorate.text.toString().trim(),
                        viewModel.getUser()!!.id,
                        addressName,
                        viewModel.getPhone(),
                        isDefault
                    )
                )
            }
        }
        viewBinding.defaultSwitch.setOnCheckedChangeListener { _, isChecked ->
            isDefault = isChecked
        }
    }

    private fun isDataOk(): Boolean {
        var isDataOk = true
        if (TextUtils.isEmpty(viewBinding.governorate.text.toString().trim())) {
            viewBinding.governorateLayout.error = getString(R.string.governorate_required)
            isDataOk = false
        }
        if (TextUtils.isEmpty(viewBinding.area.text.toString().trim())) {
            viewBinding.areaLayout.error = getString(R.string.area_required)
            isDataOk = false
        }
        if (TextUtils.isEmpty(viewBinding.city.text.toString().trim())) {
            viewBinding.cityLayout.error = getString(R.string.city_required)
            isDataOk = false
        }
        if (TextUtils.isEmpty(viewBinding.customerPhone.text.toString().trim())) {
            viewBinding.customerPhoneLayout.error = getString(R.string.phone_required)
            isDataOk = false
        }
        if (TextUtils.isEmpty(viewBinding.street.text.toString().trim())) {
            viewBinding.streetLayout.error = getString(R.string.street_required)
            isDataOk = false
        }
        return isDataOk
    }

    override fun setupObservers() {
        viewModel.addAddressLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }
                is ViewState.Success -> {
                    Toast.makeText(requireContext(),it.data.message,Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                    findNavController().navigateUp()
                }
                is ViewState.Error -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                }
            }
        })
    }

    override val layout: Int
        get() = R.layout.fragment_add_address

}