package com.khaledamin.pharmacy_android.datastore.local.repo

import com.khaledamin.pharmacy_android.datastore.remote.api.PharmacyApi
import com.khaledamin.pharmacy_android.ui.model.Category
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.SubCategory
import com.khaledamin.pharmacy_android.ui.model.requests.AddAddressRequest
import com.khaledamin.pharmacy_android.ui.model.requests.AddItemToCartRequest
import com.khaledamin.pharmacy_android.ui.model.requests.AddToFavoritesRequest
import com.khaledamin.pharmacy_android.ui.model.requests.ChangePasswordRequest
import com.khaledamin.pharmacy_android.ui.model.requests.CreateOrderRequest
import com.khaledamin.pharmacy_android.ui.model.requests.FilterRequest
import com.khaledamin.pharmacy_android.ui.model.requests.GetRelatedProductsRequest
import com.khaledamin.pharmacy_android.ui.model.requests.LoginRequest
import com.khaledamin.pharmacy_android.ui.model.requests.ProductsRequest
import com.khaledamin.pharmacy_android.ui.model.requests.RemoveItemFromCartRequest
import com.khaledamin.pharmacy_android.ui.model.requests.ReorderRequest
import com.khaledamin.pharmacy_android.ui.model.requests.ResetPasswordRequest
import com.khaledamin.pharmacy_android.ui.model.requests.SearchRequest
import com.khaledamin.pharmacy_android.ui.model.requests.SendOTPRequest
import com.khaledamin.pharmacy_android.ui.model.requests.SetDefaultAddressRequest
import com.khaledamin.pharmacy_android.ui.model.requests.SignupRequest
import com.khaledamin.pharmacy_android.ui.model.requests.UpdateUserRequest
import com.khaledamin.pharmacy_android.ui.model.requests.ValidateUserRequest
import com.khaledamin.pharmacy_android.ui.model.responses.BaseResponse
import com.khaledamin.pharmacy_android.ui.model.responses.UpdateUserResponse
import com.khaledamin.pharmacy_android.utils.SharedPreferencesManager
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

//import javax.inject.Inject
//import javax.inject.Singleton

@Singleton
class Repo
@Inject
constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val retrofit: Retrofit,
) : BaseRepo<PharmacyApi>(sharedPreferencesManager, retrofit) {
    override val apiClassInterface: Class<PharmacyApi>
        get() = PharmacyApi::class.java

    fun login(request: LoginRequest, lang: String) = api.login(request, lang)
    fun signup(request: SignupRequest,language:String) = api.signup(request,language)
    fun getCatalog() = api.getCatalog()
    fun sendOTP(lang: String, request: SendOTPRequest) = api.sendOTP(lang, request)
    fun validateOTP(lang: String, request: ValidateUserRequest) = api.validateOTP(lang, request)
    fun validateUser(lang: String, request: ValidateUserRequest) = api.validateUser(lang, request)
    fun resetPassword(lang: String, request: ResetPasswordRequest) = api.resetPassword(lang,request)
    fun getUserAddresses(id: Long) = api.getUserAddresses(id)
    fun addUserAddress(request: AddAddressRequest) = api.addUserAddress(request)
    fun setDefaultAddress(addressId: Long, request: SetDefaultAddressRequest) =
        api.setDefaultAddress(addressId, request)

    fun removeAddress(addressId: Long, request: SetDefaultAddressRequest) =
        api.removeAddress(addressId, request)

    fun getContents(lang: String) = api.getContents(lang)
    fun getRelatedProducts(request: GetRelatedProductsRequest) =
        api.getRelatedProducts(request)

    fun getCurrentOrders(id: Long) = api.getCurrentOrders(id)
    fun getPreviousOrders(id: Long) = api.getPreviousOrders(id)
    fun cancelOrder(orderId: Long) = api.cancelOrder(orderId)
    fun reorder(request:ReorderRequest) = api.reorder(request)
    fun getAddressTypes() = api.getAddressTypes()
    fun addItemToCart(request: AddItemToCartRequest) = api.addItemToCart(request)
    fun getUserCartItems(userId: Long) = api.getUserCartItems(userId)
    fun updateQuantity(request: AddItemToCartRequest) = api.updateQuantity(request)
    fun removeItemFromCart(request: RemoveItemFromCartRequest) = api.removeItemFromCart(request)
    fun getPaymentTypes() = api.getPaymentTypes()
    fun getShippingMethods() = api.getShippingMethods()
    fun createOrder(request: CreateOrderRequest) = api.createOrder(request)
    fun addToFavorites(request: AddToFavoritesRequest) = api.addToFavorites(request)
    fun removeFromFavorites(request: AddToFavoritesRequest) = api.removeFromFavorites(request)
    fun getUserFavorites(userId: Long) = api.getUserFavorites(userId)
    fun updateUser(request: UpdateUserRequest) = api.updateUser(request)
    fun changePassword(lang: String, request: ChangePasswordRequest) =
        api.changePassword(lang, request)
    fun getUserNotification(userId: Long) = api.getUserNotification(userId)
    fun getSearchResults(request:SearchRequest) = api.getSearchResults(request)
    fun getProducts(request: ProductsRequest) = api.getProducts(request)
    fun getCategories() = api.getCategories()
    fun getSubcategoriesByCategory(categoryId:Long) = api.getSubcategoriesByCategory(categoryId)
    fun findProductsBySubcategory(request: FilterRequest) = api.findProductsBySubcategory(request)
}