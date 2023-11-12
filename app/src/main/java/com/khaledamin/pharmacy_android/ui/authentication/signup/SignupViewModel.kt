package com.khaledamin.pharmacy_android.ui.authentication.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.requests.SignupRequest
import com.khaledamin.pharmacy_android.ui.model.responses.SignupResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel
@Inject
constructor(repo: Repo, apiRequestManager: ApiRequestManager) : BaseViewModel<Repo>(repo, apiRequestManager) {

    private val _signupLiveData = MutableLiveData<ViewState<SignupResponse>>()

    val signupLiveData: LiveData<ViewState<SignupResponse>>
        get() = _signupLiveData

    fun signup(request: SignupRequest,language:String) = apiRequestManager.requestApi(repo.signup(request,language),_signupLiveData)

    fun setIsVerified(isVerified:Boolean) = repo.setIsVerified(isVerified)
    fun getIsVerified() = repo.getIsVerified()
}