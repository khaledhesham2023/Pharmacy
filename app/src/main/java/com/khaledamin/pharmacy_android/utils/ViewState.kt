package com.khaledamin.pharmacy_android.utils

sealed class ViewState<out T>{
    object Loading : ViewState<Nothing>()

    class Success<out T>(val data:T, val message:String) : ViewState<T>()

    class Error(val message:String) : ViewState<Nothing>()
}