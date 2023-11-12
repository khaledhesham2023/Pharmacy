package com.khaledamin.pharmacy_android.ui.editaccount

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentEditAccountBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.ui.model.User
import com.khaledamin.pharmacy_android.ui.model.requests.UpdateUserRequest
import com.khaledamin.pharmacy_android.utils.DisplayManager.removeErrorsWhenEditing
import com.khaledamin.pharmacy_android.utils.DisplayManager.showAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditAccountFragment :
    BaseFragmentWithViewModel<FragmentEditAccountBinding, EditAccountViewModel>() {
    override val viewModelClass: Class<EditAccountViewModel>
        get() = EditAccountViewModel::class.java

    private lateinit var user: User
    private lateinit var email: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        removeErrorsWhenEditing(
            viewBinding.customerFirstnameLayout,
            viewBinding.customerLastnameLayout,
            viewBinding.customerPhoneLayout,
            viewBinding.passwordLayout,
            viewBinding.customerEmailLayout,
            viewBinding.customerUsernameLayout
        )
        user = viewModel.getUser()!!
        viewBinding.customerFirstname.setText(viewModel.getFirstname())
        viewBinding.customerLastname.setText(viewModel.getLastname())
        viewBinding.customerEmail.setText(viewModel.getEmail())
        viewBinding.customerUsername.setText(viewModel.getUserName())
        viewBinding.user = user
        viewBinding.password.setText(viewModel.getPassword())
        Log.i("TAGG",viewModel.getPassword()!!)

    }

    override fun setupObservers() {
        viewModel.updateUserLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> loadingDialog.show()
                is ViewState.Success -> {
                    Toast.makeText(requireContext(), it.data.response.message, Toast.LENGTH_SHORT)
                        .show()
                    viewModel.saveFirstName(viewBinding.customerFirstname.text.toString())
                    viewModel.saveLastname(viewBinding.customerLastname.text.toString())
                    viewModel.saveEmail(viewBinding.customerEmail.text.toString())
                    viewModel.saveUserName(viewBinding.customerUsername.text.toString())
                    findNavController().navigate(EditAccountFragmentDirections.actionEditAccountFragmentToAccountFragment())
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
        get() = R.layout.fragment_edit_account

    override fun setupListeners() {
        viewBinding.save.setOnClickListener {
            email = if (TextUtils.isEmpty(viewBinding.customerEmail.text.toString().trim())) {
                viewModel.getPhone() + "@pharmacy.com"
            } else {
                viewBinding.customerEmail.text.toString().trim()
            }
            if (isDataOK()) {
                showAlertDialog(
                    requireContext(),
                    R.string.confirmation,
                    R.string.confirm_edit,
                    R.string.confirm,
                    R.string.cancel
                ) { _, _ ->
                    viewModel.updateUser(
                        UpdateUserRequest(
                            viewModel.getUser()!!.id,
                            viewBinding.customerFirstname.text.toString().trim(),
                            viewBinding.customerLastname.text.toString().trim(),
                            viewBinding.customerUsername.text.toString().trim(),
                            email,
                            viewBinding.customerPhone.text.toString().trim(),
                            viewBinding.password.text.toString().trim()
                        )
                    )
                }
            }
        }
        viewBinding.changePasswordBtn.setOnClickListener {
            findNavController().navigate(EditAccountFragmentDirections.actionEditAccountFragmentToPhoneEntryFragment())
        }

    }

    private fun isDataOK(): Boolean {
        var isDataOk = true
        if (TextUtils.isEmpty(viewBinding.customerFirstname.text.toString().trim())) {
            viewBinding.customerFirstnameLayout.error = getString(R.string.firstname_error)
            isDataOk = false
        }
        if (TextUtils.isEmpty(viewBinding.customerLastname.text.toString().trim())) {
            viewBinding.customerLastnameLayout.error = getString(R.string.lastname_error)
            isDataOk = false
        }
        if (TextUtils.isEmpty(viewBinding.customerPhone.text.toString().trim())) {
            viewBinding.customerPhoneLayout.error = getString(R.string.phone_required)
            isDataOk = false
        }
        return isDataOk
    }


}