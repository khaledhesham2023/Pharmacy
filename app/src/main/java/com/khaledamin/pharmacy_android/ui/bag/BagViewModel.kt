package com.khaledamin.pharmacy_android.ui.bag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.Payment
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.Shipping
import com.khaledamin.pharmacy_android.ui.model.requests.AddItemToCartRequest
import com.khaledamin.pharmacy_android.ui.model.requests.CreateOrderRequest
import com.khaledamin.pharmacy_android.ui.model.requests.RemoveItemFromCartRequest
import com.khaledamin.pharmacy_android.ui.model.responses.BaseResponse
import com.khaledamin.pharmacy_android.ui.model.responses.CreateOrderResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetCategoryContentsResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BagViewModel
@Inject
constructor(repo: Repo, apiRequestManager: ApiRequestManager) :
    BaseViewModel<Repo>(repo, apiRequestManager) {

    private var _updateQuantityLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val updateQuantityLiveData: LiveData<ViewState<BaseResponse>>
        get() = _updateQuantityLiveData

    private var _getUserCartItemsLiveData = MutableLiveData<ViewState<List<Product>>>()
    val getUserCartItemsLiveData: LiveData<ViewState<List<Product>>>
        get() = _getUserCartItemsLiveData

    private var _removeItemFromCartLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val removeItemFromCartLiveData: LiveData<ViewState<BaseResponse>>
        get() = _removeItemFromCartLiveData
    private var _getPaymentTypesLiveData = MutableLiveData<ViewState<List<Payment>>>()
    val getPaymentTypesLiveData: LiveData<ViewState<List<Payment>>>
        get() = _getPaymentTypesLiveData

    private var _getShippingMethodsLiveData = MutableLiveData<ViewState<List<Shipping>>>()
    val getShippingMethodsLiveData: LiveData<ViewState<List<Shipping>>>
        get() = _getShippingMethodsLiveData

    private var _createOrderLiveData = MutableLiveData<ViewState<CreateOrderResponse>>()
    val createOrderLiveData:LiveData<ViewState<CreateOrderResponse>>
        get() = _createOrderLiveData

    fun getUserCartItems(userId: Long) =
        apiRequestManager.requestApi(repo.getUserCartItems(userId), _getUserCartItemsLiveData)

    fun updateQuantity(request: AddItemToCartRequest) =
        apiRequestManager.requestApi(repo.updateQuantity(request), _updateQuantityLiveData)

    fun removeItemFromCart(request: RemoveItemFromCartRequest) =
        apiRequestManager.requestApi(repo.removeItemFromCart(request), _removeItemFromCartLiveData)

    fun getPaymentTypes() = apiRequestManager.requestApi(repo.getPaymentTypes(),_getPaymentTypesLiveData)

    fun getShippingMethods() = apiRequestManager.requestApi(repo.getShippingMethods(),_getShippingMethodsLiveData)

    fun createOrderLiveData(request:CreateOrderRequest) = apiRequestManager.requestApi(repo.createOrder(request),_createOrderLiveData)

}