package com.khaledamin.pharmacy_android.ui.authentication.reset

import android.content.Intent
import android.hardware.display.DisplayManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ActivityPasswordBinding
import com.khaledamin.pharmacy_android.ui.authentication.login.LoginActivity
import com.khaledamin.pharmacy_android.ui.base.BaseActivityWithViewModel
import com.khaledamin.pharmacy_android.ui.model.requests.ResetPasswordRequest
import com.khaledamin.pharmacy_android.utils.DisplayManager.removeErrorsWhenEditing
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordActivity : BaseActivityWithViewModel<ActivityPasswordBinding, PasswordViewModel>() {
    override val viewModelClass: Class<PasswordViewModel>
        get() = PasswordViewModel::class.java

    override fun setupObservers() {
        viewModel.resetPasswordLiveData.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    if (!it.data.status) {
                        Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
                        loadingDialog.dismiss()
                    } else {
                        Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
                        viewModel.savePhone(null)
                        startActivity(Intent(this@PasswordActivity, LoginActivity::class.java))
                        finish()
                        loadingDialog.dismiss()
                    }
                }

                is ViewState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        removeErrorsWhenEditing(viewBinding.newPasswordLayout)
    }

    override val layout: Int
        get() = R.layout.activity_password

    override fun setupListeners() {
        viewBinding.change.setOnClickListener {
            if (isDataOk()) {
                viewModel.resetPassword(
                    ResetPasswordRequest(
                        viewModel.getPhone(),
                        viewBinding.newPassword.text.toString().trim()
                    )
                )
            }
        }
    }

    private fun isDataOk(): Boolean {
        var isDataOk = true
        if (TextUtils.isEmpty(viewBinding.newPassword.text.toString().trim())) {
            viewBinding.newPasswordLayout.error = getString(R.string.password_error)
            isDataOk = false
        }
        if (viewBinding.newPassword.text.toString().trim().length < 8) {
            viewBinding.newPasswordLayout.error = getString(R.string.password_length_error)
            isDataOk = false
        }
        return isDataOk
    }

}