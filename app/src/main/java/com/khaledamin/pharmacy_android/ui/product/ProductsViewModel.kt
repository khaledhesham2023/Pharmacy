package com.khaledamin.pharmacy_android.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.requests.AddItemToCartRequest
import com.khaledamin.pharmacy_android.ui.model.requests.RemoveItemFromCartRequest
import com.khaledamin.pharmacy_android.ui.model.responses.BaseResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetCategoryContentsResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(repo: Repo, apiRequestManager: ApiRequestManager) :
    BaseViewModel<Repo>(repo, apiRequestManager) {

    private var _getCategoryContentsLiveData =
        MutableLiveData<ViewState<GetCategoryContentsResponse>>()
    val getCategoryContentsResponse: LiveData<ViewState<GetCategoryContentsResponse>>
        get() = _getCategoryContentsLiveData

    private var _addItemToCartLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val addItemToCartLiveData:LiveData<ViewState<BaseResponse>>
        get() = _addItemToCartLiveData

    private var _updateQuantityLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val updateQuantityLiveData:LiveData<ViewState<BaseResponse>>
        get() = _updateQuantityLiveData

    private var _getUserCartItemsLiveData = MutableLiveData<ViewState<List<Product>>>()
    val getUserCartItemsLiveData:LiveData<ViewState<List<Product>>>
        get() = _getUserCartItemsLiveData

    private var _removeItemFromCartLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val removeItemFromCartLiveData:LiveData<ViewState<BaseResponse>>
        get() = _removeItemFromCartLiveData

    fun getCategoryContents(lang:String) = apiRequestManager.requestApi(repo.getContents(lang),_getCategoryContentsLiveData)
    fun addItemToCart(request:AddItemToCartRequest) = apiRequestManager.requestApi(repo.addItemToCart(request),_addItemToCartLiveData)
    fun getUserCartItems(userId:Long) = apiRequestManager.requestApi(repo.getUserCartItems(userId),_getUserCartItemsLiveData)
    fun updateQuantity(request:AddItemToCartRequest) = apiRequestManager.requestApi(repo.updateQuantity(request),_updateQuantityLiveData)
    fun removeItemFromCart(request:RemoveItemFromCartRequest) = apiRequestManager.requestApi(repo.removeItemFromCart(request),_removeItemFromCartLiveData)


}