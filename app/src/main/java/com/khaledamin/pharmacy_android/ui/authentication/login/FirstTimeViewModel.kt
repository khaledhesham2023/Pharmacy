package com.khaledamin.pharmacy_android.ui.authentication.login

import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirstTimeViewModel @Inject constructor(repo: Repo,apiRequestManager: ApiRequestManager): BaseViewModel<Repo>(repo, apiRequestManager) {
}