package com.khaledamin.pharmacy_android.ui.orders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ItemProduct2Binding
import com.khaledamin.pharmacy_android.ui.base.BaseAdapter
import com.khaledamin.pharmacy_android.ui.model.Product

class OrderDetailsAdapter(data:List<Product>) : BaseAdapter<Product,ItemProduct2Binding,OrderDetailsAdapter.OrderDetailsViewHolder>(data) {

    inner class OrderDetailsViewHolder(val binding:ItemProduct2Binding) : RecyclerView.ViewHolder(binding.root){

    }

    override val layout: Int
        get() = R.layout.item_product_2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        return OrderDetailsViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        holder.binding.product = data[position]
    }


}