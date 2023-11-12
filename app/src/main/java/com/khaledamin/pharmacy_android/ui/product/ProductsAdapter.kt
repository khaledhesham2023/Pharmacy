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
            binding.root.setOnClickListener {
                productCallback.onProductClicked(data[layoutPosition])
            }
            binding.addCartButton.setOnClickListener {
                data[layoutPosition].quantity++
                productCallback.onProductAdded(data[layoutPosition])
                notifyItemChanged(layoutPosition)
            }
            binding.plus.setOnClickListener {
                data[layoutPosition].quantity++
                productCallback.onPlusClicked(data[layoutPosition], data[layoutPosition].quantity)
                notifyItemChanged(layoutPosition)
            }
            binding.minus.setOnClickListener {
                data[layoutPosition].quantity--
                if (data[layoutPosition].quantity < 1) {
                    productCallback.onProductRemoved(data[layoutPosition])
                } else {
                    productCallback.onMinusClicked(
                        data[layoutPosition],
                        data[layoutPosition].quantity
                    )
                }
                notifyItemChanged(layoutPosition)
            }
            binding.likeIcon.setOnClickListener {
                data[layoutPosition].isLiked = !data[layoutPosition].isLiked
                if (data[layoutPosition].isLiked) {
                    productCallback.onProductLikeClicked(data[layoutPosition])
                } else {
                    productCallback.onProductDislikeClicked(data[layoutPosition])
                }
                notifyItemChanged(layoutPosition)
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
        if (data[position].isLiked) {
            holder.binding.likeIcon.setImageResource(R.drawable.ic_heart_active)
        } else {
            holder.binding.likeIcon.setImageResource(R.drawable.ic_heart_inactive)
        }
        if (data[position].quantity > 0) {
            holder.binding.addCartButton.visibility = View.GONE
            holder.binding.quantityGroup.visibility = View.VISIBLE
            holder.binding.quantity.text = data[position].quantity.toString()
        } else {
            holder.binding.addCartButton.visibility = View.VISIBLE
            holder.binding.quantityGroup.visibility = View.GONE
        }
    }
}