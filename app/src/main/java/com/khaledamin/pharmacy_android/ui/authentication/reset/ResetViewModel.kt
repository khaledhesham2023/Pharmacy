package com.khaledamin.pharmacy_android.ui.authentication.reset

import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetViewModel @Inject constructor(repo: Repo,apiRequestManager: ApiRequestManager):BaseViewModel<Repo>(repo, apiRequestManager) {

    fun saveCurrentActivity(activity: String) = repo.setCurrentActivity(activity)
}