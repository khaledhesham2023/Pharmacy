package com.khaledamin.pharmacy_android.ui.productdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentProductDetailsBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragment
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.requests.GetRelatedProductsRequest
import com.khaledamin.pharmacy_android.ui.product.ProductCallback
import com.khaledamin.pharmacy_android.ui.product.ProductsAdapter
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragmentWithViewModel<FragmentProductDetailsBinding,ProductDetailsViewModel>(),ProductCallback {

    private lateinit var product: Product
    private lateinit var productsAdapter: ProductsAdapter
    private var categoryId:Long = 0
    override val viewModelClass: Class<ProductDetailsViewModel>
        get() = ProductDetailsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product = ProductDetailsFragmentArgs.fromBundle(requireArguments()).product
//        categoryId = ProductDetailsFragmentArgs.fromBundle(requireArguments()).categoryId
        productsAdapter = ProductsAdapter(ArrayList(),this)
        viewBinding.otherProductsList.adapter = productsAdapter
        viewBinding.otherProductsList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        viewBinding.product = product
        viewModel.getRelatedProducts(categoryId,GetRelatedProductsRequest(product.productId!!))
    }

    override fun setupObservers() {
        viewModel.getRelatedProductsLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is ViewState.Loading -> {
                    loadingDialog.show()
                }
                is ViewState.Success -> {
                    productsAdapter.updateDataSet(it.data.products)
                    loadingDialog.dismiss()
                }
                is ViewState.Error -> {
                    showErrorAlertDialog(requireContext(),R.string.error,it.message,R.string.retry,R.string.cancel){
                       _,_->
                        viewModel.getRelatedProducts(categoryId, GetRelatedProductsRequest(product.productId!!))
                    }
                    loadingDialog.dismiss()
                }
            }
        })
    }

    override val layout: Int
        get() = R.layout.fragment_product_details

    override fun setupListeners() {
//        TODO("Not yet implemented")
    }

    override fun onProductClicked(product: Product) {
        findNavController().navigate(ProductDetailsFragmentDirections.actionProductDetailsFragmentSelf(product))
    }

    override fun onProductAdded(product: Product) {
//        TODO("Not yet implemented")
    }

    override fun onPlusClicked(product: Product, quantity: Int) {
        TODO("Not yet implemented")
    }

    override fun onMinusClicked(product: Product, quantity: Int) {
        TODO("Not yet implemented")
    }

    override fun onProductRemoved(product: Product) {
        TODO("Not yet implemented")
    }
}