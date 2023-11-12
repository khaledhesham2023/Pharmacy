package com.khaledamin.pharmacy_android.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.CartItem
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.requests.AddItemToCartRequest
import com.khaledamin.pharmacy_android.ui.model.requests.AddToFavoritesRequest
import com.khaledamin.pharmacy_android.ui.model.requests.RemoveItemFromCartRequest
import com.khaledamin.pharmacy_android.ui.model.requests.SearchRequest
import com.khaledamin.pharmacy_android.ui.model.responses.BaseResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(repo: Repo, apiRequestManager: ApiRequestManager) :
    BaseViewModel<Repo>(repo, apiRequestManager) {

    private var _searchLiveData = MutableLiveData<ViewState<List<Product>>>()
    val searchLiveData: LiveData<ViewState<List<Product>>>
        get() = _searchLiveData
    private var _addItemToCartLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val addItemToCartLiveData: LiveData<ViewState<BaseResponse>>
        get() = _addItemToCartLiveData
    private var _updateQuantityLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val updateQuantityLiveData: LiveData<ViewState<BaseResponse>>
        get() = _updateQuantityLiveData
    private var _removeItemFromCartLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val removeItemFromCartLiveData: LiveData<ViewState<BaseResponse>>
        get() = _removeItemFromCartLiveData
    private var _addToFavoritesLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val addToFavorites: LiveData<ViewState<BaseResponse>>
        get() = _addToFavoritesLiveData
    private var _removeFromFavoritesLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val removeFromFavoritesLiveData: LiveData<ViewState<BaseResponse>>
        get() = _removeFromFavoritesLiveData
    private var _getUserCartItemsLiveData = MutableLiveData<ViewState<List<CartItem>>>()
    val getUserCartItemsLiveData: LiveData<ViewState<List<CartItem>>>
        get() = _getUserCartItemsLiveData
    private val _getUserFavoritesLiveData = MutableLiveData<ViewState<List<Product>>>()
    val getUserFavoritesLiveData: LiveData<ViewState<List<Product>>>
        get() = _getUserFavoritesLiveData


    fun getSearchResults(request: SearchRequest) =
        apiRequestManager.requestApi(repo.getSearchResults(request), _searchLiveData)

    fun addToCart(request: AddItemToCartRequest) =
        apiRequestManager.requestApi(repo.addItemToCart(request), _addItemToCartLiveData)

    fun updateQuantity(request: AddItemToCartRequest) =
        apiRequestManager.requestApi(repo.updateQuantity(request), _updateQuantityLiveData)

    fun removeItemFromCart(request: RemoveItemFromCartRequest) =
        apiRequestManager.requestApi(repo.removeItemFromCart(request), _removeItemFromCartLiveData)

    fun addToFavorites(request: AddToFavoritesRequest) =
        apiRequestManager.requestApi(repo.addToFavorites(request), _addToFavoritesLiveData)

    fun removeFromFavorites(request: AddToFavoritesRequest) =
        apiRequestManager.requestApi(
            repo.removeFromFavorites(request),
            _removeFromFavoritesLiveData
        )

    fun getUserCartItems(userId: Long) =
        apiRequestManager.requestApi(repo.getUserCartItems(userId), _getUserCartItemsLiveData)

    fun getUserFavorites(userId: Long) = apiRequestManager.requestApi(repo.getUserFavorites(userId),_getUserFavoritesLiveData)
}