package com.khaledamin.pharmacy_android.ui.editaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.requests.ChangePasswordRequest
import com.khaledamin.pharmacy_android.ui.model.responses.BaseResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(repo: Repo,apiRequestManager: ApiRequestManager) : BaseViewModel<Repo>(repo, apiRequestManager){

    private val _changePasswordLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val changePasswordLiveData:LiveData<ViewState<BaseResponse>>
        get() = _changePasswordLiveData

    fun changePassword(lang:String,request: ChangePasswordRequest) = apiRequestManager.requestApi(repo.changePassword(lang,request),_changePasswordLiveData)
}