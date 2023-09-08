package com.khaledamin.pharmacy_android.ui.addresses

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.requests.AddAddressRequest
import com.khaledamin.pharmacy_android.ui.model.responses.BaseResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(repo: Repo, apiRequestManager: ApiRequestManager) :
    BaseViewModel<Repo>(repo, apiRequestManager) {

    private var _addAddressLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val addAddressLiveData:LiveData<ViewState<BaseResponse>>
        get() = _addAddressLiveData

    fun addUserAddress(request:AddAddressRequest) = apiRequestManager.requestApi(repo.addUserAddress(request),_addAddressLiveData)

}