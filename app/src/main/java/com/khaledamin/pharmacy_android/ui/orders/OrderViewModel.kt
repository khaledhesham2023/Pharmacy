package com.khaledamin.pharmacy_android.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.Order
import com.khaledamin.pharmacy_android.ui.model.responses.ReorderResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(repo: Repo,apiRequestManager: ApiRequestManager):BaseViewModel<Repo>(repo, apiRequestManager) {

    private var _getCurrentOrdersLiveData = MutableLiveData<ViewState<List<Order>>>()
    val getCurrentOrdersLiveData:LiveData<ViewState<List<Order>>>
        get() = _getCurrentOrdersLiveData

    private var _getPreviousOrdersLiveData = MutableLiveData<ViewState<List<Order>>>()
    val getPreviousOrdersLiveData:LiveData<ViewState<List<Order>>>
        get() = _getPreviousOrdersLiveData

    private var _reorderLiveData = MutableLiveData<ViewState<ReorderResponse>>()
    val reorderLiveData: LiveData<ViewState<ReorderResponse>>
        get() = _reorderLiveData

    fun getCurrentOrders(id:Long) = apiRequestManager.requestApi(repo.getCurrentOrders(id),_getCurrentOrdersLiveData)

    fun getPreviousOrders(id: Long) = apiRequestManager.requestApi(repo.getPreviousOrders(id),_getPreviousOrdersLiveData)

    fun reorder(userId:Long,orderId:Long) = apiRequestManager.requestApi(repo.reorder(userId,orderId),_reorderLiveData)


}