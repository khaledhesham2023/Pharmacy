package com.khaledamin.pharmacy_android.ui.editaccount

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentChangePasswordBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.ui.model.requests.ChangePasswordRequest
import com.khaledamin.pharmacy_android.utils.DisplayManager.removeErrorsWhenEditing
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment :
    BaseFragmentWithViewModel<FragmentChangePasswordBinding, ChangePasswordViewModel>() {
    override val viewModelClass: Class<ChangePasswordViewModel>
        get() = ChangePasswordViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        removeErrorsWhenEditing(
            viewBinding.oldPasswordLayout,
            viewBinding.newPasswordLayout,
            viewBinding.customerConfirmPasswordLayout
        )
    }

    override fun setupObservers() {
        viewModel.changePasswordLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> loadingDialog.show()
                is ViewState.Success -> {
                    viewModel.savePassword(viewBinding.newPassword.text.toString())
                    Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(ChangePasswordFragmentDirections.actionChangePasswordFragmentToAccountFragment())

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
        get() = R.layout.fragment_change_password

    override fun setupListeners() {
        viewBinding.confirmResetButton.setOnClickListener {
            if (isDataOk()) {
                viewModel.changePassword(
                    viewModel.getLanguage()!!,
                    ChangePasswordRequest(
                        viewBinding.oldPassword.text.toString().trim(),
                        viewBinding.newPassword.text.toString().trim(),
                        viewBinding.customerConfirmPassword.text.toString().trim(),
                        viewModel.getUser()!!.id!!
                    )
                )
            }
        }
    }

    private fun isDataOk(): Boolean {
        var isDataOk = true
        if (TextUtils.isEmpty(viewBinding.oldPassword.text.toString().trim())) {
            viewBinding.oldPasswordLayout.error = getString(R.string.old_password_required)
            isDataOk = false
        }
        if (TextUtils.isEmpty(viewBinding.newPassword.text.toString().trim())) {
            viewBinding.newPasswordLayout.error = getString(R.string.new_password_required)
            isDataOk = false
        }
        if (TextUtils.isEmpty(viewBinding.customerConfirmPassword.text.toString())) {
            viewBinding.customerConfirmPasswordLayout.error =
                getString(R.string.confirm_your_password)
            isDataOk = false
        }
        if (viewBinding.newPassword.text.toString()
                .trim() != viewBinding.customerConfirmPassword.text.toString().trim()
        ) {
            viewBinding.customerConfirmPasswordLayout.error =
                getString(R.string.password_match_error)
            viewBinding.newPasswordLayout.error = getString(R.string.password_match_error)
            isDataOk = false
        }
        return isDataOk
    }
}