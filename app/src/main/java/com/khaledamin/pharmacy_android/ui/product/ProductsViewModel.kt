package com.khaledamin.pharmacy_android.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.CartItem
import com.khaledamin.pharmacy_android.ui.model.Category
import com.khaledamin.pharmacy_android.ui.model.CategoryItem
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.SubCategory
import com.khaledamin.pharmacy_android.ui.model.requests.AddItemToCartRequest
import com.khaledamin.pharmacy_android.ui.model.requests.AddToFavoritesRequest
import com.khaledamin.pharmacy_android.ui.model.requests.FilterRequest
import com.khaledamin.pharmacy_android.ui.model.requests.ProductsRequest
import com.khaledamin.pharmacy_android.ui.model.requests.RemoveItemFromCartRequest
import com.khaledamin.pharmacy_android.ui.model.responses.BaseResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetCategoryContentsResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(repo: Repo, apiRequestManager: ApiRequestManager) :
    BaseViewModel<Repo>(repo, apiRequestManager) {

    private var _getCategoryContentsLiveData =
        MutableLiveData<ViewState<GetCategoryContentsResponse>>()
    val getCategoryContentsResponse: LiveData<ViewState<GetCategoryContentsResponse>>
        get() = _getCategoryContentsLiveData

    private var _addItemToCartLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val addItemToCartLiveData: LiveData<ViewState<BaseResponse>>
        get() = _addItemToCartLiveData

    private var _updateQuantityLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val updateQuantityLiveData: LiveData<ViewState<BaseResponse>>
        get() = _updateQuantityLiveData

    private var _getUserCartItemsLiveData = MutableLiveData<ViewState<List<CartItem>>>()
    val getUserCartItemsLiveData: LiveData<ViewState<List<CartItem>>>
        get() = _getUserCartItemsLiveData

    private var _removeItemFromCartLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val removeItemFromCartLiveData: LiveData<ViewState<BaseResponse>>
        get() = _removeItemFromCartLiveData
    private var _addToFavoritesLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val addToFavorites: LiveData<ViewState<BaseResponse>>
        get() = _addToFavoritesLiveData
    private var _removeFromFavoritesLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val removeFromFavoritesLiveData: LiveData<ViewState<BaseResponse>>
        get() = _removeFromFavoritesLiveData


    private var _getUserFavoritesLiveData = MutableLiveData<ViewState<List<Product>>>()
    val getUserFavoritesLiveData: LiveData<ViewState<List<Product>>>
        get() = _getUserFavoritesLiveData

    private var _getProductsLiveData = MutableLiveData<ViewState<List<Product>>>()
    val getProductsLiveData: LiveData<ViewState<List<Product>>>
        get() = _getProductsLiveData
    private var _getCategoriesLiveData = MutableLiveData<ViewState<List<Category>>>()
    val getCategoriesLiveData: LiveData<ViewState<List<Category>>>
        get() = _getCategoriesLiveData

    private var _getSubcategoryByCategoryId = MutableLiveData<ViewState<List<SubCategory>>>()
    val getSubcategoryByCategoryIdLiveData: LiveData<ViewState<List<SubCategory>>>
        get() = _getSubcategoryByCategoryId
    private var _getProductsBySubcategory = MutableLiveData<ViewState<List<Product>>>()
    val getProductsBySubcategoryLiveData: LiveData<ViewState<List<Product>>>
        get() = _getProductsBySubcategory


    fun getCategoryContents(lang: String) =
        apiRequestManager.requestApi(repo.getContents(lang), _getCategoryContentsLiveData)

    fun addItemToCart(request: AddItemToCartRequest) =
        apiRequestManager.requestApi(repo.addItemToCart(request), _addItemToCartLiveData)

    fun getUserCartItems(userId: Long) =
        apiRequestManager.requestApi(repo.getUserCartItems(userId), _getUserCartItemsLiveData)

    fun updateQuantity(request: AddItemToCartRequest) =
        apiRequestManager.requestApi(repo.updateQuantity(request), _updateQuantityLiveData)

    fun removeItemFromCart(request: RemoveItemFromCartRequest) =
        apiRequestManager.requestApi(repo.removeItemFromCart(request), _removeItemFromCartLiveData)

    fun addToFavorites(request: AddToFavoritesRequest) =
        apiRequestManager.requestApi(repo.addToFavorites(request), _addToFavoritesLiveData)

    fun removeFromFavorites(request: AddToFavoritesRequest) = apiRequestManager.requestApi(
        repo.removeFromFavorites(request),
        _removeFromFavoritesLiveData
    )

    fun getUserFavorites(userId: Long) =
        apiRequestManager.requestApi(repo.getUserFavorites(userId), _getUserFavoritesLiveData)

    fun getProducts(request: ProductsRequest) =
        apiRequestManager.requestApi(repo.getProducts(request), _getProductsLiveData)

    fun getCategories() = apiRequestManager.requestApi(repo.getCategories(), _getCategoriesLiveData)
    fun getSubcategoriesByCategory(categoryId: Long) = apiRequestManager.requestApi(
        repo.getSubcategoriesByCategory(categoryId),
        _getSubcategoryByCategoryId
    )

    fun getProductsBySubcategory(request: FilterRequest) = apiRequestManager.requestApi(
        repo.findProductsBySubcategory(request),
        _getProductsBySubcategory
    )


}