package com.khaledamin.pharmacy_android.ui.favorites

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.requests.AddToFavoritesRequest
import com.khaledamin.pharmacy_android.ui.model.responses.BaseResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(repo: Repo, apiRequestManager: ApiRequestManager) :
    BaseViewModel<Repo>(repo, apiRequestManager) {



    private var _getUserFavoritesLiveData = MutableLiveData<ViewState<List<Product>>>()
    val getUserFavoritesLiveData:LiveData<ViewState<List<Product>>>
        get() = _getUserFavoritesLiveData

    private var _removeFromFavoritesLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val removeFromFavoritesLiveData:LiveData<ViewState<BaseResponse>>
        get() = _removeFromFavoritesLiveData

    private var _addToFavoritesLiveData = MutableLiveData<ViewState<BaseResponse>>()
    val addToFavoritesLiveData:LiveData<ViewState<BaseResponse>>
        get() = _addToFavoritesLiveData

    fun getUserFavorites(userId:Long) = apiRequestManager.requestApi(repo.getUserFavorites(userId),_getUserFavoritesLiveData)

    fun removeFromFavorites(request: AddToFavoritesRequest) = apiRequestManager.requestApi(repo.removeFromFavorites(request),_removeFromFavoritesLiveData)

    fun addToFavorites(request: AddToFavoritesRequest) = apiRequestManager.requestApi(repo.addToFavorites(request),_addToFavoritesLiveData)

}