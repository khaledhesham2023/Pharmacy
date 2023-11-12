package com.khaledamin.pharmacy_android.ui.notification

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ItemNotificationBinding
import com.khaledamin.pharmacy_android.ui.base.BaseAdapter
import com.khaledamin.pharmacy_android.ui.model.Notification

class NotificationAdapter(data:List<Notification>):BaseAdapter<Notification,ItemNotificationBinding,NotificationAdapter.NotificationViewHolder>(data) {

    inner class NotificationViewHolder(val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override val layout: Int
        get() = R.layout.item_notification

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NotificationAdapter.NotificationViewHolder {
        return NotificationViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(
        holder: NotificationAdapter.NotificationViewHolder,
        position: Int,
    ) {
        holder.binding.notification = data[position]
    }
}