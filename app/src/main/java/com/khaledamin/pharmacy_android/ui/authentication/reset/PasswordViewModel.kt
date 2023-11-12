package com.khaledamin.pharmacy_android.ui.authentication.reset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.requests.ResetPasswordRequest
import com.khaledamin.pharmacy_android.ui.model.responses.BaseResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(repo: Repo, apiRequestManager: ApiRequestManager) :
    BaseViewModel<Repo>(repo, apiRequestManager) {

    private val _resetPasswordLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val resetPasswordLiveData: LiveData<ViewState<BaseResponse>>
        get() = _resetPasswordLiveData

    fun resetPassword(lang:String,request: ResetPasswordRequest) = apiRequestManager.requestApi(repo.resetPassword(lang,request),_resetPasswordLiveData)
}