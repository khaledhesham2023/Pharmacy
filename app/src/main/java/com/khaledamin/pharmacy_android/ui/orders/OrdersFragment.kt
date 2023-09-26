package com.khaledamin.pharmacy_android.ui.orders

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentOrdersBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.ui.model.Order
import com.khaledamin.pharmacy_android.utils.DisplayManager.showAlertDialog
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : BaseFragmentWithViewModel<FragmentOrdersBinding, OrderViewModel>(),
    CurrentOrderCallback, PreviousOrderCallback {

    private lateinit var currentOrderAdapter: CurrentOrderAdapter
    private lateinit var previousOrderAdapter: PreviousOrderAdapter
    private lateinit var selectedOrder: Order

    override val viewModelClass: Class<OrderViewModel>
        get() = OrderViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentOrderAdapter = CurrentOrderAdapter(ArrayList(), this)
        previousOrderAdapter = PreviousOrderAdapter(ArrayList(), this)
        viewBinding.currentBtn.isEnabled = false
        viewModel.getCurrentOrders(viewModel.getUser()!!.id!!)

    }

    override fun setupObservers() {
        viewModel.getCurrentOrdersLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    viewBinding.previousOrdersList.visibility = View.GONE
                    viewBinding.currentOrdersList.visibility = View.VISIBLE
                    viewBinding.currentOrdersList.adapter = currentOrderAdapter
                    viewBinding.currentOrdersList.layoutManager =
                        LinearLayoutManager(requireContext())
                    currentOrderAdapter.updateDataSet(it.data)
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
                        viewModel.getCurrentOrders(viewModel.getUser()!!.id!!)
                    }
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.getPreviousOrdersLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    viewBinding.currentOrdersList.visibility = View.GONE
                    viewBinding.previousOrdersList.visibility = View.VISIBLE
                    viewBinding.previousOrdersList.adapter = previousOrderAdapter
                    viewBinding.previousOrdersList.layoutManager =
                        LinearLayoutManager(requireContext())
                    previousOrderAdapter.updateDataSet(it.data)
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
                        viewModel.getPreviousOrders(viewModel.getUser()!!.id!!)
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
                    viewModel.getPreviousOrders(viewModel.getUser()!!.id!!)
                    loadingDialog.dismiss()
                }
                is ViewState.Error -> {
                    showErrorAlertDialog(requireContext(),R.string.error,it.message,R.string.retry,R.string.cancel){
                        _,_->
                        viewModel.reorder(viewModel.getUser()!!.id!!,selectedOrder.id!!)
                    }
                    loadingDialog.dismiss()
                }
            }
        })
    }

    override val layout: Int
        get() = R.layout.fragment_orders

    override fun setupListeners() {
        viewBinding.currentBtn.setOnClickListener {
            viewBinding.currentBtn.isEnabled = false
            viewBinding.previousBtn.isEnabled = true
            viewModel.getCurrentOrders(viewModel.getUser()!!.id!!)
        }
        viewBinding.previousBtn.setOnClickListener {
            viewBinding.currentBtn.isEnabled = true
            viewBinding.previousBtn.isEnabled = false
            viewModel.getPreviousOrders(viewModel.getUser()!!.id!!)
        }
    }

    override fun onCurrentOrderDetailsClicked(order: Order) {
        findNavController().navigate(
            OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment(
                order,
                true
            )
        )
    }

    override fun onPreviousOrderDetailsClicked(order: Order) {
        findNavController().navigate(
            OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment(
                order,
                false
            )
        )
    }

    override fun onPreviousOrderReorderClicked(order: Order) {
        showAlertDialog(
            requireContext(),
            R.string.confirmation,
            R.string.confirm_reorder,
            R.string.confirm,
            R.string.cancel
        ) { _, _ ->
            selectedOrder = order
            viewModel.reorder(viewModel.getUser()!!.id!!,selectedOrder.id!!)
        }
    }
}