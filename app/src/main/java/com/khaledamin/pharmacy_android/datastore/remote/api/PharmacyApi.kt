package com.khaledamin.pharmacy_android.datastore.remote.api

import com.khaledamin.pharmacy_android.ui.model.Address
import com.khaledamin.pharmacy_android.ui.model.requests.AddAddressRequest
import com.khaledamin.pharmacy_android.ui.model.requests.LoginRequest
import com.khaledamin.pharmacy_android.ui.model.requests.ResetPasswordRequest
import com.khaledamin.pharmacy_android.ui.model.requests.SendOTPRequest
import com.khaledamin.pharmacy_android.ui.model.requests.SetDefaultAddressRequest
import com.khaledamin.pharmacy_android.ui.model.requests.SignupRequest
import com.khaledamin.pharmacy_android.ui.model.requests.ValidateUserRequest
import com.khaledamin.pharmacy_android.ui.model.responses.BaseResponse
import com.khaledamin.pharmacy_android.ui.model.responses.SendOTPResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetCatalogResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetUserAddressesResponse
import com.khaledamin.pharmacy_android.ui.model.responses.LoginResponse
import com.khaledamin.pharmacy_android.ui.model.responses.SignupResponse
import com.khaledamin.pharmacy_android.ui.model.responses.ValidateUserResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PharmacyApi {
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Single<LoginResponse>

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
    fun setDefaultAddress(@Path("addressId") addressId:Long, @Body request: SetDefaultAddressRequest): Single<BaseResponse>

    @POST("addresses/deleteAddress/{addressId}")
    fun removeAddress(@Path("addressId") addressId: Long, @Body request: SetDefaultAddressRequest): Single<BaseResponse>
}