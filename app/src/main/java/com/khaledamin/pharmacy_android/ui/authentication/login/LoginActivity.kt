package com.khaledamin.pharmacy_android.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ActivityLoginBinding
import com.khaledamin.pharmacy_android.ui.authentication.reset.ResetActivity
import com.khaledamin.pharmacy_android.ui.authentication.signup.SignupActivity
import com.khaledamin.pharmacy_android.ui.base.BaseActivityWithViewModel
import com.khaledamin.pharmacy_android.ui.main.MainActivity
import com.khaledamin.pharmacy_android.ui.model.requests.LoginRequest
import com.khaledamin.pharmacy_android.utils.DisplayManager.removeErrorsWhenEditing
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivityWithViewModel<ActivityLoginBinding, LoginViewModel>() {
    override val viewModelClass: Class<LoginViewModel>
        get() = LoginViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        removeErrorsWhenEditing(viewBinding.usernameLayout, viewBinding.passwordLayout)
    }

    override fun setupObservers() {
        viewModel.loginLiveData.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    if (it.data.user == null) {
                        Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
                        loadingDialog.dismiss()
                    } else {
                        if (viewModel.isFirstTime(it.data.user.username!!)) {
                            viewModel.saveBearerToken(it.data.user.token!!)
                            viewModel.setLoggedIn(true)
                            viewModel.saveUser(it.data.user)
                            viewModel.saveUserName(it.data.user.username)
                            loadingDialog.dismiss()
                            startActivity(Intent(this@LoginActivity, FirstTimeActivity::class.java))
                            finish()
                        } else {
                            viewModel.setFirstTime(it.data.user.username, false)
                            viewModel.saveBearerToken(it.data.user.token!!)
                            viewModel.setLoggedIn(true)
                            viewModel.saveUser(it.data.user)
                            loadingDialog.dismiss()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }
                    }

                }


                is ViewState.Error -> {
                    showErrorAlertDialog(
                        this,
                        R.string.error_logging_in,
                        it.message,
                        R.string.retry,
                        R.string.cancel
                    ) { _, _ ->
                        viewModel.login(
                            LoginRequest(
                                viewBinding.username.text.toString().trim(),
                                viewBinding.password.text.toString().trim()
                            )
                        )
                    }
                    loadingDialog.dismiss()
                }
            }
        })
    }

    private fun isDataOk(): Boolean {
        var isDataOk = true
        if (TextUtils.isEmpty(viewBinding.username.text.toString().trim())) {
            viewBinding.usernameLayout.error = resources.getString(R.string.error_username)
            isDataOk = false
        }
        if (TextUtils.isEmpty(viewBinding.password.text.toString().trim())) {
            viewBinding.passwordLayout.error = resources.getString(R.string.password_error)
            isDataOk = false
        }
        return isDataOk
    }

    override fun setupListeners() {
        viewBinding.login.setOnClickListener {
            if (isDataOk()) {
                viewModel.login(
                    LoginRequest(
                        viewBinding.username.text.toString().trim(),
                        viewBinding.password.text.toString().trim()
                    )
                )
            }
        }
        viewBinding.createNew.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }
        viewBinding.guest.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
        viewBinding.forgetYourPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ResetActivity::class.java))
            finish()
        }
    }

    override val layout: Int
        get() = R.layout.activity_login

}