package com.khaledamin.pharmacy_android.datastore.remote.api

import com.khaledamin.pharmacy_android.ui.model.AddressType
import com.khaledamin.pharmacy_android.ui.model.CartItem
import com.khaledamin.pharmacy_android.ui.model.Category
import com.khaledamin.pharmacy_android.ui.model.CategoryItem
import com.khaledamin.pharmacy_android.ui.model.Notification
import com.khaledamin.pharmacy_android.ui.model.Order
import com.khaledamin.pharmacy_android.ui.model.Payment
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.Shipping
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
import com.khaledamin.pharmacy_android.ui.model.responses.CreateOrderResponse
import com.khaledamin.pharmacy_android.ui.model.responses.SendOTPResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetCatalogResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetCategoryContentsResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetRelatedProductsResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetUserAddressesResponse
import com.khaledamin.pharmacy_android.ui.model.responses.LoginResponse
import com.khaledamin.pharmacy_android.ui.model.responses.ReorderResponse
import com.khaledamin.pharmacy_android.ui.model.responses.SignupResponse
import com.khaledamin.pharmacy_android.ui.model.responses.UpdateUserResponse
import com.khaledamin.pharmacy_android.ui.model.responses.ValidateUserResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PharmacyApi {
    @POST("login/{language}")
    fun login(
        @Body loginRequest: LoginRequest,
        @Path("language") lang: String,
    ): Single<LoginResponse>

    @POST("signup/{lang}")
    fun signup(@Body signupRequest: SignupRequest, @Path("lang") language:String): Single<SignupResponse>

    @GET("catalog")
    fun getCatalog(): Single<GetCatalogResponse>

    @POST("{lang}/sendOTP")
    fun sendOTP(@Path("lang") lang: String, @Body request: SendOTPRequest): Single<SendOTPResponse>

    @POST("{lang}/validateOTP")
    fun validateOTP(@Path("lang") lang: String, @Body request: ValidateUserRequest): Single<ValidateUserResponse>

    @POST("{lang}/validateUser")
    fun validateUser(@Path("lang") lang: String, @Body request: ValidateUserRequest): Single<ValidateUserResponse>

    @PUT("{lang}/resetPassword")
    fun resetPassword(@Path("lang") language: String,@Body request: ResetPasswordRequest): Single<BaseResponse>

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

    @POST("products/relatedProducts")
    fun getRelatedProducts(
        @Body request: GetRelatedProductsRequest,
    ): Single<GetRelatedProductsResponse>

    @GET("orders/getCurrentOrders/{id}")
    fun getCurrentOrders(@Path("id") id: Long): Single<List<Order>>

    @GET("orders/getPreviousOrders/{id}")
    fun getPreviousOrders(@Path("id") id: Long): Single<List<Order>>

    @PUT("orders/cancelOrder/{orderId}")
    fun cancelOrder(@Path("orderId") orderId: Long): Single<BaseResponse>

    @POST("orders/reorder")
    fun reorder(
        @Body request: ReorderRequest
    ): Single<ReorderResponse>

    @GET("addresses/types")
    fun getAddressTypes(): Single<List<AddressType>>

    @POST("cart/addItemToCart")
    fun addItemToCart(@Body request: AddItemToCartRequest): Single<BaseResponse>

    @GET("cart/getUserCarts/{userId}")
    fun getUserCartItems(@Path("userId") userId: Long): Single<List<CartItem>>

    @PUT("cart/updateQuantity")
    fun updateQuantity(@Body request: AddItemToCartRequest): Single<BaseResponse>

    @POST("cart/deleteCartItem")
    fun removeItemFromCart(@Body request: RemoveItemFromCartRequest): Single<BaseResponse>

    @GET("payment")
    fun getPaymentTypes(): Single<List<Payment>>

    @GET("shipping")
    fun getShippingMethods(): Single<List<Shipping>>

    @POST("orders/createOrder")
    fun createOrder(@Body request: CreateOrderRequest): Single<CreateOrderResponse>

    @POST("favorites/add")
    fun addToFavorites(@Body request: AddToFavoritesRequest): Single<BaseResponse>

    @POST("favorites/remove")
    fun removeFromFavorites(@Body request: AddToFavoritesRequest): Single<BaseResponse>

    @GET("favorites/list/{id}")
    fun getUserFavorites(@Path("id") userId: Long): Single<List<Product>>

    @PUT("updateUser")
    fun updateUser(@Body request: UpdateUserRequest):Single<UpdateUserResponse>

    @PUT("{lang}/changePassword")
    fun changePassword(@Path("lang") lang: String,@Body request: ChangePasswordRequest):Single<BaseResponse>

    @GET("notifications/{userId}")
    fun getUserNotification(@Path("userId") userId: Long):Single<List<Notification>>

    @POST("search")
    fun getSearchResults(@Body request: SearchRequest):Single<List<Product>>

    @POST("products")
    fun getProducts(@Body request: ProductsRequest):Single<List<Product>>

    @GET("categoryItems")
    fun getCategories():Single<List<Category>>

    @GET("subcategories/{id}")
    fun getSubcategoriesByCategory(@Path("id") categoryId:Long):Single<List<SubCategory>>

    @POST("findProducts")
    fun findProductsBySubcategory(@Body request: FilterRequest):Single<List<Product>>
}