package com.khaledamin.pharmacy_android.ui.categories

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ItemCategoryBinding
import com.khaledamin.pharmacy_android.ui.base.BaseAdapter
import com.khaledamin.pharmacy_android.ui.model.Category

class CategoriesAdapter(data:List<Category>): BaseAdapter<Category,ItemCategoryBinding,CategoriesAdapter.CategoriesViewHolder>(data){

    inner class CategoriesViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root){

    }

    override val layout: Int
        get() = R.layout.item_category

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(getItemViewBinding(parent))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.binding.category = data[position]
    }

}