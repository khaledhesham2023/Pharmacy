package com.khaledamin.pharmacy_android.ui.ordercreated

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentOrderCreatedBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragment
import com.khaledamin.pharmacy_android.ui.model.Order

class OrderCreatedFragment : BaseFragment<FragmentOrderCreatedBinding>() {
    override val layout: Int
        get() = R.layout.fragment_order_created

    private lateinit var order: Order


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        order = OrderCreatedFragmentArgs.fromBundle(requireArguments()).order
        viewBinding.order = order
    }

    override fun setupListeners() {
        viewBinding.returnBtn.setOnClickListener {
            findNavController().navigate(OrderCreatedFragmentDirections.actionOrderCreatedFragmentToOrdersFragment())
        }
    }


}