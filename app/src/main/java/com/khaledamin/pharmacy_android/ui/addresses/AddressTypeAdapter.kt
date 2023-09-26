package com.khaledamin.pharmacy_android.ui.addresses

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ItemAddressTypeBinding
import com.khaledamin.pharmacy_android.ui.base.BaseAdapter
import com.khaledamin.pharmacy_android.ui.model.AddressType

class AddressTypeAdapter(data: List<AddressType>, private val callback: AddressTypeCallback) :
    BaseAdapter<AddressType, ItemAddressTypeBinding, AddressTypeAdapter.AddressTypeViewHolder>(data) {
        inner class AddressTypeViewHolder(val binding: ItemAddressTypeBinding):RecyclerView.ViewHolder(binding.root){
            init {
                binding.root.setOnClickListener {
                    callback.onAddressTypeSelected(data[layoutPosition])
                }
            }
        }

    override val layout: Int
        get() = R.layout.item_address_type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressTypeViewHolder {
        return AddressTypeViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(holder: AddressTypeViewHolder, position: Int) {
        holder.binding.addressType = data[position]
    }
}