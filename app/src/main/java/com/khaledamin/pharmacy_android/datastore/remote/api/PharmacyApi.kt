package com.khaledamin.pharmacy_android.datastore.remote.api

import com.khaledamin.pharmacy_android.ui.model.AddressType
import com.khaledamin.pharmacy_android.ui.model.Order
import com.khaledamin.pharmacy_android.ui.model.Payment
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.Shipping
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
import com.khaledamin.pharmacy_android.ui.model.responses.BaseResponse
import com.khaledamin.pharmacy_android.ui.model.responses.CreateOrderResponse
import com.khaledamin.pharmacy_android.ui.model.responses.SendOTPResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetCatalogResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetCategoryContentsResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetRelatedProductsResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetUserAddressesResponse
import com.khaledamin.pharmacy_android.ui.model.responses.LoginResponse
import com.khaledamin.pharmacy_android.ui.model.responses.ReorderResponse
import com.khaledamin.pharmacy_android.ui.model.responses.SignupResponse
import com.khaledamin.pharmacy_android.ui.model.responses.ValidateUserResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PharmacyApi {
    @POST("login/{language}")
    fun login(@Body loginRequest: LoginRequest, @Path("language") lang: String): Single<LoginResponse>

    @POST("signup")
    fun signup(@Body signupRequest: SignupRequest): Single<SignupResponse>

    @GET("catalog/{lang}")
    fun getCatalog(@Path("lang") lang: String): Single<GetCatalogResponse>

    @POST("sendOTP")
    fun sendOTP(@Body request: SendOTPRequest): Single<SendOTPResponse>

    @POST("validateOTP")
    fun validateOTP(@Body request: ValidateUserRequest): Single<ValidateUserResponse>

    @POST("validateUser")
    fun validateUser(@Body request: ValidateUserRequest): Single<ValidateUserResponse>

    @PUT("resetPassword")
    fun resetPassword(@Body request: ResetPasswordRequest): Single<BaseResponse>

    @GET("addresses/{id}")
    fun getUserAddresses(@Path("id") addressId: Long): Single<GetUserAddressesResponse>

    @POST("addresses/addAddress")
    fun addUserAddress(@Body request: AddAddressRequest): Single<BaseResponse>

    @PUT("addresses/{addressId}")
    fun setDefaultAddress(
        @Path("addressId") addressId: Long,
        @Body request: SetDefaultAddressRequest,
    ): Single<BaseResponse>

    @POST("addresses/deleteAddress/{addressId}")
    fun removeAddress(
        @Path("addressId") addressId: Long,
        @Body request: SetDefaultAddressRequest,
    ): Single<BaseResponse>

    @GET("categories/getContents/{lang}")
    fun getContents(@Path("lang") lang: String): Single<GetCategoryContentsResponse>

    @POST("products/relatedProducts/{productId}")
    fun getRelatedProducts(
        @Path("productId") id: Long,
        @Body request: GetRelatedProductsRequest,
    ): Single<GetRelatedProductsResponse>

    @GET("orders/getCurrentOrders/{id}")
    fun getCurrentOrders(@Path("id") id: Long): Single<List<Order>>

    @GET("orders/getPreviousOrders/{id}")
    fun getPreviousOrders(@Path("id") id: Long): Single<List<Order>>

    @PUT("orders/cancelOrder/{orderId}")
    fun cancelOrder(@Path("orderId") orderId:Long): Single<BaseResponse>

    @PUT("orders/reorder/{userId}/{orderId}")
    fun reorder(@Path("userId") userId:Long, @Path("orderId") orderId:Long): Single<ReorderResponse>

    @GET("addresses/types")
    fun getAddressTypes():Single<List<AddressType>>

    @POST("cart/addItemToCart")
    fun addItemToCart(@Body request: AddItemToCartRequest):Single<BaseResponse>

    @GET("cart/getUserCarts/{userId}")
    fun getUserCartItems(@Path("userId") userId: Long):Single<List<Product>>

    @PUT("cart/updateQuantity")
    fun updateQuantity(@Body request: AddItemToCartRequest):Single<BaseResponse>

    @POST("cart/deleteCartItem")
    fun removeItemFromCart(@Body request: RemoveItemFromCartRequest):Single<BaseResponse>

    @GET("payment")
    fun getPaymentTypes():Single<List<Payment>>

    @GET("shipping")
    fun getShippingMethods():Single<List<Shipping>>

    @POST("orders/createOrder")
    fun createOrder(@Body request: CreateOrderRequest):Single<CreateOrderResponse>

}