package com.khaledamin.pharmacy_android.ui.productdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.requests.GetRelatedProductsRequest
import com.khaledamin.pharmacy_android.ui.model.responses.GetCategoryContentsResponse
import com.khaledamin.pharmacy_android.ui.model.responses.GetRelatedProductsResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    repo: Repo,
    apiRequestManager: ApiRequestManager,
) : BaseViewModel<Repo>(repo, apiRequestManager) {

    private var _getRelatedProductsLiveData = MutableLiveData<ViewState<GetRelatedProductsResponse>>()
    val getRelatedProductsLiveData: LiveData<ViewState<GetRelatedProductsResponse>>
        get() = _getRelatedProductsLiveData

    fun getRelatedProducts(productId:Long, request: GetRelatedProductsRequest) = apiRequestManager.requestApi(repo.getRelatedProducts(productId,request),_getRelatedProductsLiveData)
}