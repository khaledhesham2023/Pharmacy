package com.khaledamin.pharmacy_android.ui.editaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.requests.UpdateUserRequest
import com.khaledamin.pharmacy_android.ui.model.responses.UpdateUserResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditAccountViewModel @Inject constructor(repo: Repo, apiRequestManager: ApiRequestManager) :
    BaseViewModel<Repo>(repo, apiRequestManager) {

    private var _updateUserLiveData = MutableLiveData<ViewState<UpdateUserResponse>>()
    val updateUserLiveData : LiveData<ViewState<UpdateUserResponse>>
        get() = _updateUserLiveData

    fun updateUser(request: UpdateUserRequest) = apiRequestManager.requestApi(repo.updateUser(request),_updateUserLiveData)
}