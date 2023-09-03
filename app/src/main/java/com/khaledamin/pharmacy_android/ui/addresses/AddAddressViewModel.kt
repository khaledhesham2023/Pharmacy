package com.khaledamin.pharmacy_android.ui.addresses

import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(repo: Repo,apiRequestManager: ApiRequestManager) : BaseViewModel<Repo>(repo, apiRequestManager) {
}