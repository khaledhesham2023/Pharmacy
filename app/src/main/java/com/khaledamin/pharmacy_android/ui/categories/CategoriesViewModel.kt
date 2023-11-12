package com.khaledamin.pharmacy_android.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.responses.GetCatalogResponse
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel
@Inject
constructor(repo: Repo, apiRequestManager: ApiRequestManager) : BaseViewModel<Repo>(repo, apiRequestManager) {

    private val _catalogLiveData = MutableLiveData<ViewState<GetCatalogResponse>>()

    val catalogLiveData: LiveData<ViewState<GetCatalogResponse>>
        get() = _catalogLiveData

    fun getCatalog() = apiRequestManager.requestApi(repo.getCatalog(),_catalogLiveData)
    fun saveCategoryId(categoryId:Long) = repo.saveCategoryId(categoryId)
}