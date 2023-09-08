package com.khaledamin.pharmacy_android.datastore.local.repo

import com.khaledamin.pharmacy_android.datastore.remote.api.PharmacyApi
import com.khaledamin.pharmacy_android.ui.model.requests.AddAddressRequest
import com.khaledamin.pharmacy_android.ui.model.requests.LoginRequest
import com.khaledamin.pharmacy_android.ui.model.requests.ResetPasswordRequest
import com.khaledamin.pharmacy_android.ui.model.requests.SendOTPRequest
import com.khaledamin.pharmacy_android.ui.model.requests.SetDefaultAddressRequest
import com.khaledamin.pharmacy_android.ui.model.requests.SignupRequest
import com.khaledamin.pharmacy_android.ui.model.requests.ValidateUserRequest
import com.khaledamin.pharmacy_android.utils.SharedPreferencesManager
import retrofit2.Retrofit
import retrofit2.http.Body
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

    fun login(request: LoginRequest) = api.login(request)
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

}