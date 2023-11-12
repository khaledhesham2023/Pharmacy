package com.khaledamin.pharmacy_android.ui.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentNotificationsBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : BaseFragmentWithViewModel<FragmentNotificationsBinding,NotificationsViewModel>() {
    override val viewModelClass: Class<NotificationsViewModel>
        get() = NotificationsViewModel::class.java

    private lateinit var notificationAdapter: NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationAdapter = NotificationAdapter(ArrayList())
        viewBinding.notificationsList.adapter = notificationAdapter
        viewBinding.notificationsList.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getUserNotifications(viewModel.getUser()!!.id!!)
    }

    override fun setupObservers() {
        viewModel.notificationLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is ViewState.Loading -> loadingDialog.show()
                is ViewState.Success -> {
                    notificationAdapter.updateDataSet(it.data)
                    loadingDialog.dismiss()
                }
                is ViewState.Error -> {
                    showErrorAlertDialog(requireContext(),R.string.error, it.message,R.string.retry,R.string.cancel){
                        _,_->
                        viewModel.getUserNotifications(viewModel.getUser()!!.id!!)
                    }
                }
            }
        })
    }

    override val layout: Int
        get() = R.layout.fragment_notifications

    override fun setupListeners() {
//        TODO("Not yet implemented")
    }

}

