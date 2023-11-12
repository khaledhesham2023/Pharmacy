package com.khaledamin.pharmacy_android.ui.base

import androidx.lifecycle.ViewModel
import com.khaledamin.pharmacy_android.datastore.local.repo.BaseRepo
import com.khaledamin.pharmacy_android.ui.model.Address
import com.khaledamin.pharmacy_android.ui.model.User
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.Constants


abstract class BaseViewModel<R : BaseRepo<*>>(
    protected val repo: R, protected val apiRequestManager: ApiRequestManager,
) : ViewModel() {

    fun saveBearerToken(token: String?) = repo.saveBearerToken(token)

    fun getBearerToken() = repo.getBearerToken()

    fun setFirstTime(username: String, isFirst: Boolean) = repo.setFirstTime(username, isFirst)

    fun isFirstTime(username: String) = repo.isFirstTime(username)

    fun saveUser(user: User?) = repo.saveUser(user)

    fun getUser() = repo.getUser()

    fun saveUserName(username: String?) = repo.saveUsername(username)

    fun getUserName() = repo.getUsername()

    fun setLoggedIn(isLoggedIn: Boolean) = repo.setLoggedIn(isLoggedIn)

    fun isLoggedIn() = repo.isLoggedIn()

    fun saveLanguage(language: String) = repo.saveLanguage(language)

    fun getLanguage() = repo.getLanguage()

    fun savePhone(phone: String?) = repo.savePhone(phone)

    fun getPhone() = repo.getPhone()
    fun savePassword(password: String) = repo.savePassword(password)
    fun getPassword() = repo.getPassword()
    fun saveFirstName(name: String) = repo.saveFirstName(name)
    fun getFirstname() = repo.getFirstName()
    fun saveLastname(name: String) = repo.saveLastName(name)
    fun getLastname() = repo.getLastName()
    fun setCurrentActivity(activity:String) = repo.setCurrentActivity(activity)
    fun getCurrentActivity() = repo.getCurrentActivity()
    fun saveEmail(email:String) = repo.saveEmail(email)
    fun getEmail() = repo.getEmail()
    fun saveDefaultAddress(address: Address) = repo.saveDefaultAddress(address)
    fun getDefaultAddress() = repo.getDefaultAddress()

}