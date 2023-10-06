package com.khaledamin.pharmacy_android.ui.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.ui.addresses.AddressTypeAdapter
import com.khaledamin.pharmacy_android.ui.addresses.AddressTypeCallback
import com.khaledamin.pharmacy_android.ui.model.AddressType

abstract class AddressTypesDialog(
    private val context: Context,
    private val list: List<AddressType>,
) : Dialog(context), AddressTypeCallback {

    private lateinit var addressTypeAdapter: AddressTypeAdapter

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_address_type_list, null)
        setContentView(view)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        setupRecyclerView(view)

    }

    @SuppressLint("CutPasteId")
    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.dialog_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        addressTypeAdapter = AddressTypeAdapter(list, this)
        recyclerView.adapter = addressTypeAdapter
    }
}