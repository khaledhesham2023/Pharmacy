package com.khaledamin.pharmacy_android.datastore.local.repo

import com.khaledamin.pharmacy_android.datastore.remote.api.PharmacyApi
import com.khaledamin.pharmacy_android.ui.model.requests.AddAddressRequest
import com.khaledamin.pharmacy_android.ui.model.requests.AddItemToCartRequest
import com.khaledamin.pharmacy_android.ui.model.requests.CreateOrderRequest
import com.khaledamin.pharmacy_android.ui.model.requests.GetRelatedProductsRequest
import com.khaledamin.pharmacy_android.ui.model.requests.LoginRequest
import com.khaledamin.pharmacy_android.ui.model.requests.RemoveItemFromCartRequest
import com.khaledamin.pharmacy_android.ui.model.requests.ResetPasswordRequest
import com.khaledamin.pharmacy_android.ui.model.requests.SendOTPRequest
import com.khaledamin.pharmacy_android.ui.model.requests.SetDefaultAddressRequest
import com.khaledamin.pharmacy_android.ui.model.requests.SignupRequest
import com.khaledamin.pharmacy_android.ui.model.requests.ValidateUserRequest
import com.khaledamin.pharmacy_android.utils.SharedPreferencesManager
import retrofit2.Retrofit
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

    fun login(request: LoginRequest,lang: String) = api.login(request,lang)
    fun signup(request: SignupRequest) = api.signup(request)
    fun getCatalog(lang: String) = api.getCatalog(lang)
    fun sendOTP(request: SendOTPRequest) = api.sendOTP(request)
    fun validateOTP(request: ValidateUserRequest) = api.validateOTP(request)
    fun validateUser(request: ValidateUserRequest) = api.validateUser(request)
    fun resetPassword(request: ResetPasswordRequest) = api.resetPassword(request)
    fun getUserAddresses(id: Long) = api.getUserAddresses(id)
    fun addUserAddress(request: AddAddressRequest) = api.addUserAddress(request)
    fun setDefaultAddress(addressId: Long, request: SetDefaultAddressRequest) =
        api.setDefaultAddress(addressId, request)
    fun removeAddress(addressId: Long, request: SetDefaultAddressRequest) =
        api.removeAddress(addressId, request)
    fun getContents(lang:String) = api.getContents(lang)
    fun getRelatedProducts(productId: Long, request: GetRelatedProductsRequest) =
        api.getRelatedProducts(productId, request)
    fun getCurrentOrders(id: Long) = api.getCurrentOrders(id)
    fun getPreviousOrders(id: Long) = api.getPreviousOrders(id)
    fun cancelOrder(orderId:Long) = api.cancelOrder(orderId)
    fun reorder(userId:Long,orderId:Long) = api.reorder(userId,orderId)
    fun getAddressTypes() = api.getAddressTypes()
    fun addItemToCart(request: AddItemToCartRequest) = api.addItemToCart(request)
    fun getUserCartItems(userId:Long) = api.getUserCartItems(userId)
    fun updateQuantity(request: AddItemToCartRequest) = api.updateQuantity(request)
    fun removeItemFromCart(request:RemoveItemFromCartRequest) = api.removeItemFromCart(request)
    fun getPaymentTypes() = api.getPaymentTypes()
    fun getShippingMethods() = api.getShippingMethods()
    fun createOrder(request:CreateOrderRequest) = api.createOrder(request)
}