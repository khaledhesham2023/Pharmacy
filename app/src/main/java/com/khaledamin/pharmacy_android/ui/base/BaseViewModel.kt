package com.khaledamin.pharmacy_android.ui.base

import androidx.lifecycle.ViewModel
import com.khaledamin.pharmacy_android.datastore.local.repo.BaseRepo
import com.khaledamin.pharmacy_android.ui.model.User
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.Constants


abstract class BaseViewModel<R : BaseRepo<*>>(
    protected val repo: R, protected val apiRequestManager: ApiRequestManager,
) : ViewModel() {

    fun saveBearerToken(token:String?) = repo.saveBearerToken(token)

    fun getBearerToken() = repo.getBearerToken()

    fun setFirstTime(username: String,isFirst:Boolean) = repo.setFirstTime(username,isFirst)

    fun isFirstTime(username: String) = repo.isFirstTime(username)

    fun saveUser(user: User?) = repo.saveUser(user)

    fun getUser() = repo.getUser()

    fun saveUserName(username:String?) = repo.saveUsername(username)

    fun getUserName() = repo.getUsername()

    fun setLoggedIn(isLoggedIn:Boolean) = repo.setLoggedIn(isLoggedIn)

    fun isLoggedIn() = repo.isLoggedIn()

    fun saveLanguage(language:String) = repo.saveLanguage(language)

    fun getLanguage() = repo.getLanguage()

    fun savePhone(phone:String?) = repo.savePhone(phone)

    fun getPhone() = repo.getPhone()

}