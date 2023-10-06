package com.khaledamin.pharmacy_android.ui.product

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ItemProductBinding
import com.khaledamin.pharmacy_android.ui.base.BaseAdapter
import com.khaledamin.pharmacy_android.ui.model.Product

class ProductsAdapter(data: List<Product>, private val productCallback: ProductCallback) :
    BaseAdapter<Product, ItemProductBinding, ProductsAdapter.ProductsViewHolder>(data) {

    inner class ProductsViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            var quantity = 0
            binding.quantityGroup.visibility = View.GONE
            binding.addCartButton.setOnClickListener {
                quantity ++
                binding.addCartButton.visibility = View.GONE
                binding.quantityGroup.visibility = View.VISIBLE
                binding.quantity.text = quantity.toString()
                productCallback.onProductAdded(data[layoutPosition])
            }
            binding.plus.setOnClickListener {
                quantity ++
                productCallback.onPlusClicked(data[layoutPosition],quantity)
                binding.quantity.text = quantity.toString()
            }
            binding.minus.setOnClickListener {
                if (quantity == 1){
                    binding.quantityGroup.visibility = View.GONE
                    binding.addCartButton.visibility = View.VISIBLE
                } else {
                    quantity --
                    productCallback.onMinusClicked(data[layoutPosition],quantity)
                    binding.quantity.text = quantity.toString()
                }
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

    fun setCartProductsQuantities(data: List<Product>) {
        this.data = data

    }
}