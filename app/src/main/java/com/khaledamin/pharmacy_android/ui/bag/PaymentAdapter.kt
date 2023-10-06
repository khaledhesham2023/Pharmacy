package com.khaledamin.pharmacy_android.ui.bag

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ItemPaymentBinding
import com.khaledamin.pharmacy_android.ui.base.BaseAdapter
import com.khaledamin.pharmacy_android.ui.model.Payment

class PaymentAdapter(data:List<Payment>,private val callback: PaymentCallback):BaseAdapter<Payment,ItemPaymentBinding,PaymentAdapter.PaymentViewHolder>(data) {

    inner class PaymentViewHolder(val binding: ItemPaymentBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                callback.onPaymentTypeSelected(data[layoutPosition])
            }
        }
    }

    override val layout: Int
        get() = R.layout.item_payment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        return PaymentViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        holder.binding.payment = data[position]
    }
}