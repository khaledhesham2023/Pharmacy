package com.khaledamin.pharmacy_android.datastore.local.repo

import com.khaledamin.pharmacy_android.ui.model.Address
import com.khaledamin.pharmacy_android.ui.model.User
import com.khaledamin.pharmacy_android.utils.Constants
import com.khaledamin.pharmacy_android.utils.SharedPreferencesManager
import retrofit2.Retrofit

abstract class BaseRepo<T>(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val retrofit: Retrofit,
) {

    abstract val apiClassInterface: Class<T>
    val api: T = retrofit.create(apiClassInterface)
    fun saveBearerToken(bearerToken: String?) =
        sharedPreferencesManager.saveBearerToken(bearerToken)
    fun getBearerToken() = sharedPreferencesManager.getBearerToken()
    fun setFirstTime(username: String, isFirst: Boolean) =
        sharedPreferencesManager.setFirstTime(username, isFirst)
    fun isFirstTime(username: String) = sharedPreferencesManager.isFirstTime(username)
    fun saveUsername(username: String?) = sharedPreferencesManager.saveUsername(username)
    fun getUsername() = sharedPreferencesManager.getUsername()
    fun saveUser(user: User?) = sharedPreferencesManager.saveUser(user)
    fun getUser() = sharedPreferencesManager.getUser()
    fun setLoggedIn(isLoggedIn: Boolean) = sharedPreferencesManager.setLoggedIn(isLoggedIn)
    fun isLoggedIn() = sharedPreferencesManager.isLoggedIn()
    fun saveLanguage(language: String) = sharedPreferencesManager.saveLanguage(language)
    fun getLanguage() = sharedPreferencesManager.getLanguage()
    fun savePhone(phone: String?) = sharedPreferencesManager.savePhone(phone)
    fun getPhone() = sharedPreferencesManager.getPhone()
    fun savePassword(password: String) = sharedPreferencesManager.savePassword(password)
    fun getPassword() = sharedPreferencesManager.getPassword()
    fun saveCategoryId(categoryId:Long) = sharedPreferencesManager.saveCategoryId(categoryId)
    fun getCategoryId() = sharedPreferencesManager.getCategoryId()
    fun setIsVerified(isVerified:Boolean) = sharedPreferencesManager.setIsVerified(isVerified)
    fun getIsVerified() = sharedPreferencesManager.getIsVerified()
    fun setCurrentActivity(activity:String) = sharedPreferencesManager.setCurrentActivity(activity)
    fun getCurrentActivity() = sharedPreferencesManager.getCurrentActivity()
    fun saveFirstName(name:String) = sharedPreferencesManager.saveFirstName(name)
    fun getFirstName() = sharedPreferencesManager.getFirstName()
    fun saveLastName(name:String) = sharedPreferencesManager.saveLastName(name)
    fun getLastName() = sharedPreferencesManager.getLastName()
    fun saveEmail(email:String) = sharedPreferencesManager.saveEmail(email)
    fun getEmail() = sharedPreferencesManager.getEmail()

    fun saveDefaultAddress(address: Address) = sharedPreferencesManager.saveDefaultAddress(address)
    fun getDefaultAddress() = sharedPreferencesManager.getDefaultAddress()
}