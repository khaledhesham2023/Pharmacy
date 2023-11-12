package com.khaledamin.pharmacy_android.ui.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentOrderDetailsBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragment
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.ui.model.Order
import com.khaledamin.pharmacy_android.ui.model.requests.ReorderRequest
import com.khaledamin.pharmacy_android.utils.DisplayManager.showAlertDialog
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailsFragment :
    BaseFragmentWithViewModel<FragmentOrderDetailsBinding, OrderDetailsViewModel>() {

    private lateinit var order: Order
    private lateinit var orderDetailsAdapter: OrderDetailsAdapter
    private var isCurrent = false

    override val layout: Int
        get() = R.layout.fragment_order_details
    override val viewModelClass: Class<OrderDetailsViewModel>
        get() = OrderDetailsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        order = OrderDetailsFragmentArgs.fromBundle(requireArguments()).order
        isCurrent = OrderDetailsFragmentArgs.fromBundle(requireArguments()).isCurrent
        if (!isCurrent) {
            viewBinding.cancelButton.visibility = View.GONE
            viewBinding.reorderButton.visibility = View.VISIBLE
        } else {
            viewBinding.cancelButton.visibility = View.VISIBLE
            viewBinding.reorderButton.visibility = View.GONE
        }
        viewBinding.order = order
        orderDetailsAdapter = OrderDetailsAdapter(order.products!!)
        viewBinding.productsList.adapter = orderDetailsAdapter
        viewBinding.productsList.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun setupObservers() {
        viewModel.cancelOrderLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                    loadingDialog.dismiss()
                }

                is ViewState.Error -> {
                    showErrorAlertDialog(
                        requireContext(),
                        R.string.error,
                        it.message,
                        R.string.retry,
                        R.string.cancel
                    ) { _, _ ->
                        viewModel.cancelOrder(order.id!!)
                    }
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.reorderLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is ViewState.Loading -> {
                    loadingDialog.show()
                }
                is ViewState.Success -> {
                    findNavController().navigate(OrderDetailsFragmentDirections.actionOrderDetailsFragmentSelf(it.data.order,true))
                    loadingDialog.dismiss()
                }
                is ViewState.Error -> {
                    showErrorAlertDialog(requireContext(),R.string.error,it.message,R.string.retry,R.string.cancel){
                        _,_->
                        viewModel.reorder(ReorderRequest(order.id!!,viewModel.getUser()!!.id!!))
                    }
                    loadingDialog.dismiss()
                }
            }
        })
    }

    override fun setupListeners() {
        viewBinding.cancelButton.setOnClickListener {
            showAlertDialog(
                requireContext(),
                R.string.confirmation,
                R.string.confirm_cancel,
                R.string.confirm,
                R.string.cancel
            ) { _, _ ->
                viewModel.cancelOrder(order.id!!)
            }
        }
        viewBinding.reorderButton.setOnClickListener {
            showAlertDialog(
                requireContext(),
                R.string.confirmation,
                R.string.confirm_cancel,
                R.string.confirm,
                R.string.cancel
            ) { _, _ ->
                viewModel.reorder(ReorderRequest(order.id!!,viewModel.getUser()!!.id!!))
            }
        }
    }

}