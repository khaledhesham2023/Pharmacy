package com.khaledamin.pharmacy_android.ui.productdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.CartItem
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.requests.AddItemToCartRequest
import com.khaledamin.pharmacy_android.ui.model.requests.AddToFavoritesRequest
import com.khaledamin.pharmacy_android.ui.model.requests.GetRelatedProductsRequest
import com.khaledamin.pharmacy_android.ui.model.requests.RemoveItemFromCartRequest
import com.khaledamin.pharmacy_android.ui.model.responses.BaseResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetCategoryContentsResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetRelatedProductsResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    repo: Repo,
    apiRequestManager: ApiRequestManager,
) : BaseViewModel<Repo>(repo, apiRequestManager) {

    private var _getRelatedProductsLiveData = MutableLiveData<ViewState<GetRelatedProductsResponse>>()
    val getRelatedProductsLiveData: LiveData<ViewState<GetRelatedProductsResponse>>
        get() = _getRelatedProductsLiveData

    private var _getCartItemsLiveData = MutableLiveData<ViewState<List<CartItem>>>()
    val getCartItemsLiveData:LiveData<ViewState<List<CartItem>>>
        get() = _getCartItemsLiveData
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
    val removeFromFavoritesLiveData:LiveData<ViewState<BaseResponse>>
        get() = _removeFromFavoritesLiveData
    private var _getUserFavoritesLiveData = MutableLiveData<ViewState<List<Product>>>()
    val getUserFavoritesLiveData:LiveData<ViewState<List<Product>>>
        get() = _getUserFavoritesLiveData
    fun getRelatedProducts(request: GetRelatedProductsRequest) = apiRequestManager.requestApi(repo.getRelatedProducts(request),_getRelatedProductsLiveData)
    fun addToCart(request: AddItemToCartRequest) =
        apiRequestManager.requestApi(repo.addItemToCart(request), _addItemToCartLiveData)

    fun updateQuantity(request: AddItemToCartRequest) =
        apiRequestManager.requestApi(repo.updateQuantity(request), _updateQuantityLiveData)

    fun removeItemFromCart(request: RemoveItemFromCartRequest) =
        apiRequestManager.requestApi(repo.removeItemFromCart(request), _removeItemFromCartLiveData)

    fun addToFavorites(request: AddToFavoritesRequest) =
        apiRequestManager.requestApi(repo.addToFavorites(request), _addToFavoritesLiveData)

    fun removeFromFavorites(request: AddToFavoritesRequest) =
        apiRequestManager.requestApi(repo.removeFromFavorites(request),_removeFromFavoritesLiveData)

    fun getCartItems(userId:Long) = apiRequestManager.requestApi(repo.getUserCartItems(userId),_getCartItemsLiveData)

    fun getUserFavorites(userId: Long) = apiRequestManager.requestApi(repo.getUserFavorites(userId),_getUserFavoritesLiveData)

}