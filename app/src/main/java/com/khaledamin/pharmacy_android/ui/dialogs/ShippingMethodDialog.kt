package com.khaledamin.pharmacy_android.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.ui.addresses.AddressTypeAdapter
import com.khaledamin.pharmacy_android.ui.bag.PaymentAdapter
import com.khaledamin.pharmacy_android.ui.bag.PaymentCallback
import com.khaledamin.pharmacy_android.ui.bag.ShippingAdapter
import com.khaledamin.pharmacy_android.ui.bag.ShippingCallback
import com.khaledamin.pharmacy_android.ui.model.Payment
import com.khaledamin.pharmacy_android.ui.model.Shipping

abstract class ShippingMethodDialog(private val context: Context, private val list: List<Shipping>) :
    Dialog(context), ShippingCallback {

    private lateinit var adapter: ShippingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_shipping_methods_list, null)
        setContentView(view)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        setupPaymentList(view)
    }


    private fun setupPaymentList(view: View) {
        val shippingList = view.findViewById<RecyclerView>(R.id.dialog_list)
        adapter = ShippingAdapter(list,this)
        shippingList.adapter = adapter
        shippingList.layoutManager = LinearLayoutManager(context)

    }
}