package com.khaledamin.pharmacy_android.ui.bag

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ItemBagItemBinding
import com.khaledamin.pharmacy_android.ui.base.BaseAdapter
import com.khaledamin.pharmacy_android.ui.model.CartItem
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.product.ProductCallback

class BagItemAdapter(data: List<CartItem>, private val callback: BagItemCallback) :
    BaseAdapter<CartItem, ItemBagItemBinding, BagItemAdapter.BagItemViewHolder>(data) {

    inner class BagItemViewHolder(val binding: ItemBagItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.plus.setOnClickListener {
                var quantity = Integer.parseInt(binding.quantity.text.toString())
                quantity +=1
                callback.onPlusClicked(quantity, data[layoutPosition])
                binding.quantity.text = quantity.toString()
            }
            binding.minus.setOnClickListener {
                var quantity = Integer.parseInt(binding.quantity.text.toString())
                if (quantity == 1) {
                    callback.onProductRemoved(data[layoutPosition])
                } else {
                    quantity -= 1
                    callback.onMinusClicked(quantity, data[layoutPosition])
                    binding.quantity.text = quantity.toString()

                }
            }
            binding.remove.setOnClickListener {
                callback.onProductRemoved(data[layoutPosition])
            }
        }
    }

    override val layout: Int
        get() = R.layout.item_bag_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BagItemViewHolder {
        return BagItemViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(holder: BagItemViewHolder, position: Int) {
        holder.binding.cartItem = data[position]
    }

}