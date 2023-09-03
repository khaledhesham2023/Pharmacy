package com.khaledamin.pharmacy_android.ui.authentication.verification

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ActivityVerificationBinding
import com.khaledamin.pharmacy_android.ui.authentication.reset.PasswordActivity
import com.khaledamin.pharmacy_android.ui.base.BaseActivityWithViewModel
import com.khaledamin.pharmacy_android.ui.model.requests.SendOTPRequest
import com.khaledamin.pharmacy_android.ui.model.requests.ValidateUserRequest
import com.khaledamin.pharmacy_android.utils.Constants
import com.khaledamin.pharmacy_android.utils.Constants.ONE_MINUTE
import com.khaledamin.pharmacy_android.utils.Constants.ONE_SECOND
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationActivity :
    BaseActivityWithViewModel<ActivityVerificationBinding, VerificationViewModel>() {
    override val viewModelClass: Class<VerificationViewModel>
        get() = VerificationViewModel::class.java

    private lateinit var phoneNumber: String
    private var enableResend = false
    private var selectedPosition = 0
    private var otp = ""
    private var otpEntry = ""
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            TODO("Not yet implemented")
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            TODO("Not yet implemented")
        }

        override fun afterTextChanged(editable: Editable?) {
            if (editable!!.isNotEmpty()) {
                when (selectedPosition) {
                    0 -> {
                        selectedPosition = 1
                        showKeyBoard(viewBinding.secondNumber)
                    }

                    1 -> {
                        selectedPosition = 2
                        showKeyBoard(viewBinding.thirdNumber)
                    }

                    2 -> {
                        selectedPosition = 3
                        showKeyBoard(viewBinding.fourthNumber)
                    }

                    else -> {
                        viewBinding.verifyButton.isEnabled = true
                    }
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.firstNumber.addTextChangedListener(textWatcher)
        viewBinding.secondNumber.addTextChangedListener(textWatcher)
        viewBinding.thirdNumber.addTextChangedListener(textWatcher)
        viewBinding.fourthNumber.addTextChangedListener(textWatcher)
        phoneNumber = "+2" + viewModel.getPhone()
        viewModel.sendOTP(SendOTPRequest(phoneNumber))
    }

    override val layout: Int
        get() = R.layout.activity_verification


    private fun showTimer() {
        enableResend = false
        viewBinding.resendCode.setTextColor(resources.getColor(android.R.color.holo_red_dark))
        object : CountDownTimer(ONE_MINUTE * 1000L,ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                viewBinding.resendCode.text = getString(R.string.resendCodeAfter,millisUntilFinished/1000)
                viewBinding.resendCode.isClickable = false
            }

            override fun onFinish() {
                enableResend = true
                viewBinding.resendCode.setText(R.string.resendCode)
                viewBinding.resendCode.setTextColor(resources.getColor(android.R.color.black))
                viewBinding.resendCode.isClickable = true
            }

        }.start()
    }

    override fun setupObservers() {
        viewModel.sendOTPLiveData.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    otp = it.data.otp!!
                    Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                    showTimer()
                }

                is ViewState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                }
            }
        })

        viewModel.validateOTPLiveData.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@VerificationActivity,PasswordActivity::class.java))
                    finish()
                    loadingDialog.dismiss()
                }

                is ViewState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                }
            }
        })
    }

    override fun setupListeners() {
        viewBinding.backArrow.setOnClickListener {
            finish()
        }
        viewBinding.resendCode.setOnClickListener {
            if (enableResend){
                viewModel.sendOTP(SendOTPRequest(phoneNumber))
            }
        }
        viewBinding.verifyButton.setOnClickListener {
            otpEntry = viewBinding.firstNumber.text.toString()
                .plus(viewBinding.secondNumber.text.toString())
                .plus(viewBinding.thirdNumber.text.toString())
                .plus(viewBinding.fourthNumber.text.toString())
            if (otpEntry == otp){
                Log.i("LOGGGG",otp + otpEntry)
                viewModel.validateOTP(ValidateUserRequest(otpEntry,viewModel.getPhone()))
            }
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            when (selectedPosition) {
                3 -> {
                    selectedPosition = 2
                    showKeyBoard(viewBinding.thirdNumber)
                }

                2 -> {
                    selectedPosition = 1
                    showKeyBoard(viewBinding.secondNumber)
                }

                1 -> {
                    selectedPosition = 0
                    showKeyBoard(viewBinding.firstNumber)
                }

            }
            viewBinding.verifyButton.isEnabled = false
            return true
        } else {
            return super.onKeyUp(keyCode, event)
        }
    }

    private fun showKeyBoard(editText: EditText) {
        editText.requestFocus()
        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

}