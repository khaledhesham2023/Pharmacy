package com.khaledamin.pharmacy_android.ui.authentication.signup

import android.hardware.display.DisplayManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ActivitySignupBinding
import com.khaledamin.pharmacy_android.ui.base.BaseActivityWithViewModel
import com.khaledamin.pharmacy_android.ui.model.requests.SignupRequest
import com.khaledamin.pharmacy_android.utils.DisplayManager.removeErrorsWhenEditing
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

//import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : BaseActivityWithViewModel<ActivitySignupBinding, SignupViewModel>() {
    override val viewModelClass: Class<SignupViewModel>
        get() = SignupViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        removeErrorsWhenEditing(
            viewBinding.customerFirstnameLayout, viewBinding.customerLastnameLayout,
            viewBinding.customerPasswordLayout, viewBinding.customerConfirmPasswordLayout,
            viewBinding.customerUsernameLayout, viewBinding.customerPhoneLayout
        )
    }

    override fun setupObservers() {
        viewModel.signupLiveData.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }
                is ViewState.Success -> {
                    Toast.makeText(this,it.data.message,Toast.LENGTH_SHORT).show()
                    viewModel.setFirstTime(it.data.username!!,true)
                    loadingDialog.dismiss()
                    finish()
                }
                is ViewState.Error -> {
                    loadingDialog.dismiss()
                    showErrorAlertDialog(this,R.string.error_sign_up,it.message,R.string.retry,R.string.cancel){
                        _,_->
                        viewModel.signup(
                            SignupRequest(
                                viewBinding.customerFirstname.text.toString().trim(),
                                viewBinding.customerLastname.text.toString().trim(),
                                viewBinding.customerPassword.text.toString().trim(),
                                viewBinding.customerUsername.text.toString().trim(),
                                viewBinding.customerPhone.text.toString().trim(),
                                createEmail(viewBinding.customerEmail.text.toString().trim())
                            )
                        )
                    }
                }
            }
        })
    }

    override fun setupListeners() {
        viewBinding.loginText.setOnClickListener {
            finish()
        }
        viewBinding.createNewButton.setOnClickListener {
            if (isDataOk()) {
                viewModel.signup(
                    SignupRequest(
                        viewBinding.customerFirstname.text.toString().trim(),
                        viewBinding.customerLastname.text.toString().trim(),
                        viewBinding.customerPassword.text.toString().trim(),
                        viewBinding.customerUsername.text.toString().trim(),
                        viewBinding.customerPhone.text.toString().trim(),
                        createEmail(viewBinding.customerEmail.text.toString().trim())
                    )
                )
            }
        }
    }

    private fun createEmail(email: String): String {
        return if (TextUtils.isEmpty(email)) {
            viewBinding.customerPhone.text.toString() + "@pharmacy.com"
        } else {
            email
        }
    }

    private fun isDataOk(): Boolean {
        var isDataOk = true
        if (TextUtils.isEmpty(viewBinding.customerFirstname.text.toString())) {
            viewBinding.customerFirstnameLayout.error = getString(R.string.firstname_error)
            isDataOk = false
        }
        if (TextUtils.isEmpty(viewBinding.customerLastname.text.toString())) {
            viewBinding.customerLastnameLayout.error = getString(R.string.firstname_error)
            isDataOk = false
        }
        if (TextUtils.isEmpty(viewBinding.customerPassword.text.toString())) {
            viewBinding.customerPasswordLayout.error = getString(R.string.password_error)
            isDataOk = false
        }
        if (viewBinding.customerPassword.text.toString() != viewBinding.customerConfirmPassword.text.toString()) {
            viewBinding.customerPasswordLayout.error = getString(R.string.password_match_error)
            viewBinding.customerConfirmPasswordLayout.error =
                getString(R.string.password_match_error)
            isDataOk = false
        }
        if (TextUtils.isEmpty(viewBinding.customerUsername.text.toString())) {
            viewBinding.customerUsernameLayout.error = getString(R.string.error_username)
            isDataOk = false
        }
        if (TextUtils.isEmpty(viewBinding.customerPhone.text.toString())) {
            viewBinding.customerPhoneLayout.error = getString(R.string.error_phone)
            isDataOk = false
        }
        if (viewBinding.customerPhone.text.toString().length != 11) {
            viewBinding.customerPhoneLayout.error = getString(R.string.error_phone_eleven)
            isDataOk = false
        }
        if (viewBinding.customerPassword.text.toString().length < 8) {
            viewBinding.customerPasswordLayout.error = getString(R.string.password_length_error)
            isDataOk = false
        }
        return isDataOk
    }

    override val layout: Int
        get() = R.layout.activity_signup

}