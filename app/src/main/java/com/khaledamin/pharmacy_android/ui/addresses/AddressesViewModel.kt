package com.khaledamin.pharmacy_android.ui.addresses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.Address
import com.khaledamin.pharmacy_android.ui.model.requests.SetDefaultAddressRequest
import com.khaledamin.pharmacy_android.ui.model.responses.BaseResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetUserAddressesResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressesViewModel @Inject constructor(repo: Repo, apiRequestManager: ApiRequestManager) :
    BaseViewModel<Repo>(repo, apiRequestManager) {

    private var _userAddressesLiveData = MutableLiveData<ViewState<GetUserAddressesResponse>>()
    val userAddressesLiveData: LiveData<ViewState<GetUserAddressesResponse>>
        get() = _userAddressesLiveData

    private var _setDefaultAddressLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val setDefaultAddressLiveData: LiveData<ViewState<BaseResponse>>
        get() = _setDefaultAddressLiveData

    private var _removeAddressLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val removeAddressLiveData: LiveData<ViewState<BaseResponse>>
        get() = _removeAddressLiveData

    fun getUserAddresses(id: Long) =
        apiRequestManager.requestApi(repo.getUserAddresses(id), _userAddressesLiveData)

    fun setDefaultAddress(addressId: Long, request: SetDefaultAddressRequest) =
        apiRequestManager.requestApi(
            repo.setDefaultAddress(addressId, request),
            _setDefaultAddressLiveData
        )

    fun removeAddress(addressId: Long, request: SetDefaultAddressRequest) =
        apiRequestManager.requestApi(repo.removeAddress(addressId, request), _removeAddressLiveData)
}