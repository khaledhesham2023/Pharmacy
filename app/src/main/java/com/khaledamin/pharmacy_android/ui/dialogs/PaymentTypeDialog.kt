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
import com.khaledamin.pharmacy_android.ui.model.Payment

abstract class PaymentTypeDialog(private val context: Context, private val list: List<Payment>) :
    Dialog(context), PaymentCallback {

    private lateinit var adapter: PaymentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_payment_type_list, null)
        setContentView(view)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        setupPaymentList(view)
    }


    private fun setupPaymentList(view: View) {
        val paymentList = view.findViewById<RecyclerView>(R.id.dialog_list)
        adapter = PaymentAdapter(list,this)
        paymentList.adapter = adapter
        paymentList.layoutManager = LinearLayoutManager(context)

    }
}