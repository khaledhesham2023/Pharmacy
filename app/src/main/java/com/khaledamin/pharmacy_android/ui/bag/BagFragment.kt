package com.khaledamin.pharmacy_android.ui.bag

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentBagBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.ui.dialogs.PaymentTypeDialog
import com.khaledamin.pharmacy_android.ui.dialogs.ShippingMethodDialog
import com.khaledamin.pharmacy_android.ui.model.CartItem
import com.khaledamin.pharmacy_android.ui.model.Payment
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.Shipping
import com.khaledamin.pharmacy_android.ui.model.requests.AddItemToCartRequest
import com.khaledamin.pharmacy_android.ui.model.requests.CreateOrderRequest
import com.khaledamin.pharmacy_android.ui.model.requests.RemoveItemFromCartRequest
import com.khaledamin.pharmacy_android.utils.DisplayManager.showAlertDialog
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BagFragment : BaseFragmentWithViewModel<FragmentBagBinding, BagViewModel>(), BagItemCallback {
    override val viewModelClass: Class<BagViewModel>
        get() = BagViewModel::class.java

    private lateinit var bagItemAdapter: BagItemAdapter
    private lateinit var paymentTypes: ArrayList<Payment>
    private lateinit var shippingMethods: ArrayList<Shipping>
    private var shippingId: Long = 0
    private var paymentId: Long = 0
    private var total:Double = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bagItemAdapter = BagItemAdapter(ArrayList(), this)
        paymentTypes = ArrayList()
        shippingMethods = ArrayList()
        viewBinding.cartItems.adapter = bagItemAdapter
        viewBinding.cartItems.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getUserCartItems(viewModel.getUser()!!.id!!)
    }

    override fun setupListeners() {
        viewBinding.addButton.setOnClickListener {
            findNavController().navigate(BagFragmentDirections.actionBagFragmentToProductsFragment(1))
        }
        viewBinding.paymentTypeMenu.setOnClickListener {
            viewModel.getPaymentTypes()
        }
        viewBinding.shippingTypeMenu.setOnClickListener {
            viewModel.getShippingMethods()
        }
        viewBinding.createOrder.setOnClickListener {
            showAlertDialog(requireContext(),R.string.confirmation,R.string.confirm_create_order,R.string.confirm,R.string.cancel){
                _,_->
                viewModel.createOrderLiveData(
                    CreateOrderRequest(
                        viewModel.getUser()!!.id!!,
                        shippingId,
                        paymentId))

            }
        }
    }

    override fun setupObservers() {
        viewModel.getUserCartItemsLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    total = 0.0
                    if (it.data.isNotEmpty()) {
                        viewBinding.emptyView.visibility = View.GONE
                        viewBinding.bagView.visibility = View.VISIBLE
                        for (cartItem:CartItem in it.data){
                            total += (cartItem.quantity?.times(cartItem.product!!.packPrice!!)!!)
                        }
                        viewBinding.total.text = total.toString().plus(" L.E")
                        bagItemAdapter.updateDataSet(it.data)
                    } else {
                        viewBinding.emptyView.visibility = View.VISIBLE
                        viewBinding.bagView.visibility = View.GONE
                    }

                    loadingDialog.dismiss()
                }

                is ViewState.Error -> {
                    viewBinding.emptyView.visibility = View.GONE
                    viewBinding.bagView.visibility = View.GONE
                    showErrorAlertDialog(
                        requireContext(),
                        R.string.error,
                        it.message,
                        R.string.retry,
                        R.string.cancel
                    ) { _, _ ->
                        viewModel.getUserCartItems(viewModel.getUser()!!.id!!)
                    }
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.updateQuantityLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    Snackbar.make(
                        requireContext(),
                        viewBinding.root,
                        it.data.message!!,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    viewModel.getUserCartItems(viewModel.getUser()!!.id!!)
                    loadingDialog.dismiss()
                }

                is ViewState.Error -> {
                    Snackbar.make(
                        requireContext(),
                        viewBinding.root,
                        it.message,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.removeItemFromCartLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    Snackbar.make(
                        requireContext(),
                        viewBinding.root,
                        it.data.message!!,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    viewModel.getUserCartItems(viewModel.getUser()!!.id!!)
                    loadingDialog.dismiss()
                }

                is ViewState.Error -> {
                    Snackbar.make(
                        requireContext(),
                        viewBinding.root,
                        it.message,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    loadingDialog.dismiss()
                }

            }
        })
        viewModel.getPaymentTypesLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    paymentTypes = it.data as ArrayList
                    showPaymentTypesList()
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
                        viewModel.getPaymentTypes()
                    }
                    loadingDialog.dismiss()
                }
            }
        })

        viewModel.getShippingMethodsLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    shippingMethods = it.data as ArrayList
                    showShippingMethodsList()
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
                        viewModel.getShippingMethods()
                    }
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.createOrderLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> loadingDialog.show()
                is ViewState.Success -> {
                    findNavController().navigate(
                        BagFragmentDirections.actionBagFragmentToOrderCreatedFragment(
                            it.data.order
                        )
                    )
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
                        viewModel.createOrderLiveData(
                            CreateOrderRequest(
                                viewModel.getUser()!!.id!!,
                                shippingId,
                                paymentId
                            )
                        )
                    }
                    loadingDialog.dismiss()
                }
            }
        })

    }

    private fun showShippingMethodsList() {
        val shippingList = object : ShippingMethodDialog(requireContext(), shippingMethods) {
            override fun onShippingMethodSelected(shipping: Shipping) {
                viewBinding.shippingType.text = shipping.shippingName
                shippingId = shipping.shippingId!!
                dismiss()
            }
        }
        shippingList.show()
    }

    private fun showPaymentTypesList() {
        val paymentList = object : PaymentTypeDialog(requireContext(), paymentTypes) {
            override fun onPaymentTypeSelected(payment: Payment) {
                viewBinding.paymentType.text = payment.paymentName
                paymentId = payment.paymentId!!
                dismiss()
            }

        }
        paymentList.show()
    }

    override val layout: Int
        get() = R.layout.fragment_bag

    override fun onPlusClicked(quantity: Int, cartItem: CartItem) {
        viewModel.updateQuantity(
            AddItemToCartRequest(
                viewModel.getUser()!!.id!!,
                cartItem.product!!.productId,
                quantity
            )
        )
    }

    override fun onMinusClicked(quantity: Int, cartItem: CartItem) {
        viewModel.updateQuantity(
            AddItemToCartRequest(
                viewModel.getUser()!!.id!!,
                cartItem.product!!.productId,
                quantity
            )
        )
    }

    override fun onProductRemoved(cartItem: CartItem) {
        viewModel.removeItemFromCart(
            RemoveItemFromCartRequest(
                viewModel.getUser()!!.id!!,
                cartItem.product!!.productId
            )
        )
    }
}