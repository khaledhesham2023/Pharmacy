package com.khaledamin.pharmacy_android.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.responses.GetCategoryContentsResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(repo: Repo, apiRequestManager: ApiRequestManager) :
    BaseViewModel<Repo>(repo, apiRequestManager) {

    private var _getCategoryContentsLiveData =
        MutableLiveData<ViewState<GetCategoryContentsResponse>>()
    val getCategoryContentsResponse: LiveData<ViewState<GetCategoryContentsResponse>>
        get() = _getCategoryContentsLiveData

    fun getCategoryContents() = apiRequestManager.requestApi(repo.getContents(),_getCategoryContentsLiveData)
}