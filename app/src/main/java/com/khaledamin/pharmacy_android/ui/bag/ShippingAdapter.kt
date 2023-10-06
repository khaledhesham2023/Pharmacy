package com.khaledamin.pharmacy_android.ui.bag

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ItemPaymentBinding
import com.khaledamin.pharmacy_android.databinding.ItemShippingBinding
import com.khaledamin.pharmacy_android.ui.base.BaseAdapter
import com.khaledamin.pharmacy_android.ui.model.Payment
import com.khaledamin.pharmacy_android.ui.model.Shipping

class ShippingAdapter(data:List<Shipping>, private val callback: ShippingCallback):BaseAdapter<Shipping,ItemShippingBinding,ShippingAdapter.ShippingViewHolder>(data) {

    inner class ShippingViewHolder(val binding: ItemShippingBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                callback.onShippingMethodSelected(data[layoutPosition])
            }
        }
    }

    override val layout: Int
        get() = R.layout.item_shipping

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShippingViewHolder {
        return ShippingViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(holder: ShippingViewHolder, position: Int) {
        holder.binding.shipping = data[position]
    }
}