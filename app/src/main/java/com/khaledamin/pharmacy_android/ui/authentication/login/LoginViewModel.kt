package com.khaledamin.pharmacy_android.ui.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.requests.LoginRequest
import com.khaledamin.pharmacy_android.ui.model.responses.LoginResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel
@Inject
constructor(repo: Repo,apiRequestManager: ApiRequestManager): BaseViewModel<Repo>(repo, apiRequestManager) {

    private var _loginLiveData = MutableLiveData<ViewState<LoginResponse>>()
    val loginLiveData: LiveData<ViewState<LoginResponse>>
        get() = _loginLiveData

    fun login(request: LoginRequest,lang:String) = apiRequestManager.requestApi(repo.login(request,lang),_loginLiveData)

}