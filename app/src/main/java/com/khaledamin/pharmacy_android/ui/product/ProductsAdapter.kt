package com.khaledamin.pharmacy_android.ui.product

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ItemProductBinding
import com.khaledamin.pharmacy_android.ui.base.BaseAdapter
import com.khaledamin.pharmacy_android.ui.model.Product

class ProductsAdapter(data: List<Product>, private val productCallback: ProductCallback) :
    BaseAdapter<Product,ItemProductBinding,ProductsAdapter.ProductsViewHolder>(data) {

        inner class ProductsViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root){
            init {
                binding.root.setOnClickListener {
                    productCallback.onProductClicked(data[layoutPosition])
                }
            }
        }

    override val layout: Int
        get() = R.layout.item_product

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.binding.product = data[position]
    }
}