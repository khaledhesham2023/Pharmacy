package com.khaledamin.pharmacy_android.ui.editaccount

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.navigation.fragment.findNavController
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentPhoneEntryBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragment
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.utils.DisplayManager.removeErrorsWhenEditing
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneEntryFragment : BaseFragment<FragmentPhoneEntryBinding>() {
    override val layout: Int
        get() = R.layout.fragment_phone_entry


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        removeErrorsWhenEditing(viewBinding.mobileNumberLayout)
    }
    override fun setupListeners() {
        viewBinding.confirm.setOnClickListener {
            if (isDataOk()) {
                findNavController().navigate(PhoneEntryFragmentDirections.actionPhoneEntryFragmentToVerificationFragment(viewBinding.mobileNumber.text.toString()))
            }
        }
    }

    private fun isDataOk(): Boolean {
        var isDataOk = true
        if (TextUtils.isEmpty(viewBinding.mobileNumber.text.toString().trim())) {
            viewBinding.mobileNumberLayout.error = getString(R.string.phone_required)
            isDataOk = false
        }
        if (viewBinding.mobileNumber.text.toString().trim().length > 11
            || viewBinding.mobileNumber.text.toString().trim().length < 11
        ) {
            viewBinding.mobileNumberLayout.error = getString(R.string.error_phone_eleven)
            isDataOk = false
        }
        return isDataOk
    }

}