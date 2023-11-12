package com.khaledamin.pharmacy_android.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.base.BaseViewModel
import com.khaledamin.pharmacy_android.ui.model.Notification
import com.khaledamin.pharmacy_android.utils.ApiRequestManager
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(repo: Repo,apiRequestManager: ApiRequestManager) : BaseViewModel<Repo>(repo, apiRequestManager) {

    private var _notificationsLiveData = MutableLiveData<ViewState<List<Notification>>>()
    val notificationLiveData:LiveData<ViewState<List<Notification>>>
        get() = _notificationsLiveData

    fun getUserNotifications(userId:Long) = apiRequestManager.requestApi(repo.getUserNotification(userId),_notificationsLiveData)
}