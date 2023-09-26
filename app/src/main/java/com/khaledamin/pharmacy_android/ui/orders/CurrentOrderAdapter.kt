package com.khaledamin.pharmacy_android.ui.orders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ItemCurrentOrderBinding
import com.khaledamin.pharmacy_android.ui.base.BaseAdapter
import com.khaledamin.pharmacy_android.ui.model.Order

class CurrentOrderAdapter(data: List<Order>, private val callback: CurrentOrderCallback) :
    BaseAdapter<Order, ItemCurrentOrderBinding, CurrentOrderAdapter.OrderViewHolder>(data) {

    inner class OrderViewHolder(val binding: ItemCurrentOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.orderDetailsBtn.setOnClickListener {
                callback.onCurrentOrderDetailsClicked(data[layoutPosition])
            }
        }
    }

    override val layout: Int
        get() = R.layout.item_current_order

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.binding.order = data[position]
    }
}