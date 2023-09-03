package com.khaledamin.pharmacy_android.ui.addresses

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ItemAddressBinding
import com.khaledamin.pharmacy_android.ui.base.BaseAdapter
import com.khaledamin.pharmacy_android.ui.model.Address

class AddressesAdapter(data:List<Address>) : BaseAdapter<Address,ItemAddressBinding, AddressesAdapter.AddressViewHolder>(data) {
    inner class AddressViewHolder(val binding:ItemAddressBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override val layout: Int
        get() = R.layout.item_address

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.binding.address = data[position]
    }

}