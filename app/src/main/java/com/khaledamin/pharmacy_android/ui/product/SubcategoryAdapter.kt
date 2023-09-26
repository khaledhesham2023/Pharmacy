package com.khaledamin.pharmacy_android.ui.product

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ItemSubcategoryBinding
import com.khaledamin.pharmacy_android.ui.base.BaseAdapter
import com.khaledamin.pharmacy_android.ui.model.SubCategory

class SubcategoryAdapter(
    data: List<SubCategory>,
    private val subcategoryCallback: SubcategoryCallback,
) :
    BaseAdapter<SubCategory, ItemSubcategoryBinding, SubcategoryAdapter.SubcategoryViewHolder>(data) {
    inner class SubcategoryViewHolder(val binding: ItemSubcategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                subcategoryCallback.onSubcategoryClicked(data[layoutPosition], layoutPosition)
            }
        }
    }

    override val layout: Int
        get() = R.layout.item_subcategory

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoryViewHolder {
        return SubcategoryViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(holder: SubcategoryViewHolder, position: Int) {
        holder.binding.subcategory = data[position]
    }
}