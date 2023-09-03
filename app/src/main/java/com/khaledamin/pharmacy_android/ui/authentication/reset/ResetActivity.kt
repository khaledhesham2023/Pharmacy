package com.khaledamin.pharmacy_android.ui.authentication.reset

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ActivityResetBinding
import com.khaledamin.pharmacy_android.ui.authentication.verification.VerificationActivity
import com.khaledamin.pharmacy_android.ui.base.BaseActivity
import com.khaledamin.pharmacy_android.ui.base.BaseActivityWithViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetActivity : BaseActivityWithViewModel<ActivityResetBinding, ResetViewModel>() {
    override val viewModelClass: Class<ResetViewModel>
        get() = ResetViewModel::class.java

    override fun setupObservers() {
//        TODO("Not yet implemented")
    }

    override val layout: Int
        get() = R.layout.activity_reset

    override fun setupListeners() {
        viewBinding.reset.setOnClickListener {
            if (isDataOk()) {
                viewModel.savePhone(viewBinding.mobileNumber.text.toString())
                startActivity(Intent(this@ResetActivity, VerificationActivity::class.java))
            }
        }
    }

    private fun isDataOk(): Boolean {
        var isDataOk = true
        if (TextUtils.isEmpty(viewBinding.mobileNumber.text.toString().trim())) {
            viewBinding.mobileNumberLayout.error = getString(R.string.error_phone)
            isDataOk = false
        }
        if (viewBinding.mobileNumber.text.toString().length != 11) {
            viewBinding.mobileNumberLayout.error = getString(R.string.error_phone_eleven)
            isDataOk = false
        }
        return isDataOk
    }

}