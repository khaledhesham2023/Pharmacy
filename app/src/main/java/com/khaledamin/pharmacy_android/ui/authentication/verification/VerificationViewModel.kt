package com.khaledamin.pharmacy_android.ui.authentication.verification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.requests.SendOTPRequest
import com.khaledamin.pharmacy_android.ui.model.requests.ValidateUserRequest
import com.khaledamin.pharmacy_android.ui.model.responses.SendOTPResponse
import com.khaledamin.pharmacy_android.ui.model.responses.ValidateUserResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(repo: Repo, apiRequestManager: ApiRequestManager) : BaseViewModel<Repo>(repo, apiRequestManager) {

    private val _sendOTPLiveData = MutableLiveData<ViewState<SendOTPResponse>>()
    val sendOTPLiveData:LiveData<ViewState<SendOTPResponse>>
        get() = _sendOTPLiveData

    private val _validateUserLiveData = MutableLiveData<ViewState<ValidateUserResponse>>()
    val validateUserLiveData:LiveData<ViewState<ValidateUserResponse>>
        get() = _validateUserLiveData

    private val _validateOTPLiveData = MutableLiveData<ViewState<ValidateUserResponse>>()
    val validateOTPLiveData:LiveData<ViewState<ValidateUserResponse>>
        get() = _validateOTPLiveData

    fun sendOTP(request:SendOTPRequest) = apiRequestManager.requestApi(repo.sendOTP(request),_sendOTPLiveData)

    fun validateUser(request: ValidateUserRequest) = apiRequestManager.requestApi(repo.validateUser(request),_validateUserLiveData)

    fun validateOTP(request: ValidateUserRequest) = apiRequestManager.requestApi(repo.validateOTP(request),_validateOTPLiveData)

}