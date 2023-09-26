package com.khaledamin.pharmacy_android.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.responses.BaseResponse
import com.khaledamin.pharmacy_android.ui.model.responses.ReorderResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(repo: Repo, apiRequestManager: ApiRequestManager) :
    BaseViewModel<Repo>(repo, apiRequestManager) {

    private var _cancelOrderLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val cancelOrderLiveData: LiveData<ViewState<BaseResponse>>
        get() = _cancelOrderLiveData

    private var _reorderLiveData = MutableLiveData<ViewState<ReorderResponse>>()
    val reorderLiveData: LiveData<ViewState<ReorderResponse>>
        get() = _reorderLiveData

    fun cancelOrder(orderId:Long) = apiRequestManager.requestApi(repo.cancelOrder(orderId),_cancelOrderLiveData)

    fun reorder(userId:Long,orderId:Long) = apiRequestManager.requestApi(repo.reorder(userId,orderId),_reorderLiveData)
}