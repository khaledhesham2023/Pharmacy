package com.khaledamin.pharmacy_android.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.CartItem
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repo: Repo,apiRequestManager: ApiRequestManager):BaseViewModel<Repo>(repo, apiRequestManager) {

    private var _getCartItemsLiveData = MutableLiveData<ViewState<List<CartItem>>>()
     var isBadgeShown = MutableLiveData(true)
     var cartItemsCount = MutableLiveData(0)


    val getCartItemsLiveData:LiveData<ViewState<List<CartItem>>>
        get() = _getCartItemsLiveData
    fun getCartItems(userId:Long) = apiRequestManager.requestApi(repo.getUserCartItems(userId),_getCartItemsLiveData)
}