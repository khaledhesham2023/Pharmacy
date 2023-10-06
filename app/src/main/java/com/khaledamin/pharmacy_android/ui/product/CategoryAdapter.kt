package com.khaledamin.pharmacy_android.ui.product

import android.graphics.Color
import android.view.View
import android.view.View.OnScrollChangeListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ItemCategory2Binding
import com.khaledamin.pharmacy_android.ui.base.BaseAdapter
import com.khaledamin.pharmacy_android.ui.model.Category
import com.khaledamin.pharmacy_android.ui.model.CategoryItem

class CategoryAdapter(data: List<CategoryItem>, private val callback: CategoryItemCallback) :
    BaseAdapter<CategoryItem, ItemCategory2Binding, CategoryAdapter.CategoryViewHolder>(data) {

    var selectedCategory:CategoryItem? = null
    inner class CategoryViewHolder(val binding: ItemCategory2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                callback.onCategoryItemClicked(data[layoutPosition], layoutPosition)
                if (selectedCategory != null){
                    selectedCategory!!.isSelected = false
                }
                selectedCategory = data[layoutPosition]
                selectedCategory!!.isSelected = true
                notifyDataSetChanged()
            }

        }
    }

    override val layout: Int
        get() = R.layout.item_category_2


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryAdapter.CategoryViewHolder {
        return CategoryViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        holder.binding.category = data[position]
        if (data[position].isSelected){
            holder.binding.tabLine.setBackgroundColor(Color.BLUE)
            holder.binding.tabLine.visibility = View.VISIBLE
        } else {
            holder.binding.tabLine.visibility = View.GONE
        }
    }

}