package com.khaledamin.pharmacy_android.ui.orders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ItemPreviousOrderBinding
import com.khaledamin.pharmacy_android.ui.base.BaseAdapter
import com.khaledamin.pharmacy_android.ui.model.Order

class PreviousOrderAdapter(data: List<Order>, private val callback: PreviousOrderCallback) :
    BaseAdapter<Order, ItemPreviousOrderBinding, PreviousOrderAdapter.PreviousOrderViewHolder>(data) {

    inner class PreviousOrderViewHolder(val binding: ItemPreviousOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.orderDetailsBtn.setOnClickListener {
                callback.onPreviousOrderDetailsClicked(data[layoutPosition])
            }
            binding.reorderBtn.setOnClickListener {
                callback.onPreviousOrderReorderClicked(data[layoutPosition])
            }
        }
    }

    override val layout: Int
        get() = R.layout.item_previous_order

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviousOrderViewHolder {
        return PreviousOrderViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(holder: PreviousOrderViewHolder, position: Int) {
        holder.binding.order = data[position]
    }

}