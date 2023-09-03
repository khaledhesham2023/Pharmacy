package com.khaledamin.pharmacy_android.utils

import android.content.Context
import com.google.gson.Gson
import com.khaledamin.pharmacy_android.ui.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

//import dagger.hilt.android.qualifiers.ApplicationContext
//import javax.inject.Inject
//import javax.inject.Singleton

@Singleton
class SharedPreferencesManager
@Inject
constructor(@ApplicationContext context: Context) {

    private var sharedPreferences =
        context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun saveBearerToken(bearerToken: String?) =
        sharedPreferences.edit().putString(Constants.BEARER_TOKEN, bearerToken).apply()

    fun getBearerToken(): String? = sharedPreferences.getString(Constants.BEARER_TOKEN, "")

    fun setFirstTime(username: String,isFirst:Boolean) = sharedPreferences.edit().putBoolean(username,isFirst).apply()

    fun isFirstTime(username: String) = sharedPreferences.getBoolean(username,false)

    fun saveUsername(username:String?) = sharedPreferences.edit().putString(Constants.USERNAME,username).apply()

    fun getUsername() = sharedPreferences.getString(Constants.USERNAME,"")

    fun saveUser(user: User?) = sharedPreferences.edit().putString(Constants.USER,Gson().toJson(user)).apply()

    fun getUser(): User? = Gson().fromJson(sharedPreferences.getString(Constants.USER,""), User::class.java)

    fun setLoggedIn(isLoggedIn: Boolean) = sharedPreferences.edit().putBoolean(Constants.IS_LOGGED_IN,isLoggedIn).apply()

    fun isLoggedIn() = sharedPreferences.getBoolean(Constants.IS_LOGGED_IN,false)

    fun saveLanguage(language:String) = sharedPreferences.edit().putString(Constants.CURRENT_LANGUAGE,language).apply()

    fun getLanguage() = sharedPreferences.getString(Constants.CURRENT_LANGUAGE,"")

    fun savePhone(phone:String?) = sharedPreferences.edit().putString(Constants.USER_PHONE,phone).apply()

    fun getPhone() = sharedPreferences.getString(Constants.USER_PHONE,"")

}