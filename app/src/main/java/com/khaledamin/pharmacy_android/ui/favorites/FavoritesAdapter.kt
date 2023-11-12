package com.khaledamin.pharmacy_android.ui.favorites

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ItemFavoriteBinding
import com.khaledamin.pharmacy_android.ui.base.BaseAdapter
import com.khaledamin.pharmacy_android.ui.model.Product

class FavoritesAdapter(data: List<Product>, private val callback: FavoritesCallback) :
    BaseAdapter<Product, ItemFavoriteBinding, FavoritesAdapter.FavoritesViewHolder>(data) {

    inner class FavoritesViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback.onFavoriteProductClicked(data[layoutPosition])
            }
            binding.likeIcon.setOnClickListener {
                callback.onLikeIconClicked(data[layoutPosition])
                notifyDataSetChanged()
            }
        }
    }

    override val layout: Int
        get() = R.layout.item_favorite

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.binding.product = data[position]
    }
}