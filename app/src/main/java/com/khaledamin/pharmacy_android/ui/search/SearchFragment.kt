package com.khaledamin.pharmacy_android.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentSearchBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.ui.model.CartItem
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.requests.AddItemToCartRequest
import com.khaledamin.pharmacy_android.ui.model.requests.AddToFavoritesRequest
import com.khaledamin.pharmacy_android.ui.model.requests.RemoveItemFromCartRequest
import com.khaledamin.pharmacy_android.ui.model.requests.SearchRequest
import com.khaledamin.pharmacy_android.ui.product.ProductCallback
import com.khaledamin.pharmacy_android.ui.product.ProductsAdapter
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragmentWithViewModel<FragmentSearchBinding, SearchViewModel>(),
    ProductCallback {

    private lateinit var searchAdapter: ProductsAdapter
    private lateinit var selectedQuery: String
    private lateinit var cartItems: List<CartItem>
    private lateinit var products: List<Product>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        products = ArrayList()
        searchAdapter = ProductsAdapter(ArrayList(), this)
        viewBinding.searchList.adapter = searchAdapter
        viewBinding.searchList.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    override val viewModelClass: Class<SearchViewModel>
        get() = SearchViewModel::class.java

    override fun setupObservers() {
        viewModel.searchLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    products = it.data
                    searchAdapter.updateDataSet(products)
                    viewModel.getUserCartItems(viewModel.getUser()!!.id!!)
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
                        viewModel.getSearchResults(SearchRequest(selectedQuery))
                    }
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.addItemToCartLiveData.observe(viewLifecycleOwner, Observer {
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
        viewModel.addToFavorites.observe(viewLifecycleOwner, Observer {
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
        viewModel.removeFromFavoritesLiveData.observe(viewLifecycleOwner, Observer {
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
        viewModel.getUserCartItemsLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> loadingDialog.show()
                is ViewState.Success -> {
                    cartItems = it.data
                    loadingDialog.dismiss()
                    modifyQuantities(cartItems)
                    viewModel.getUserFavorites(viewModel.getUser()!!.id!!)
                }

                is ViewState.Error -> {
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.getUserFavoritesLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is ViewState.Loading -> {
                    loadingDialog.show()
                }
                is ViewState.Success -> {
                    setUserFavorites(it.data)
                    loadingDialog.dismiss()
                }
                is ViewState.Error -> {
                    loadingDialog.dismiss()
                }
            }
        })
    }
    private fun modifyQuantities(cartProducts: List<CartItem>) {
        for (cartProduct in cartProducts) {
            for (product in products) {
                if (product.productId == cartProduct.product!!.productId) {
                    product.quantity = cartProduct.quantity!!
                }
            }
        }
        searchAdapter.notifyDataSetChanged()
    }
    private fun setUserFavorites(likedProducts:List<Product>){
        for (likedProduct in likedProducts){
            for (product in products){
                if (product.productId == likedProduct.productId){
                    product.isLiked = true
                }
            }
        }
        searchAdapter.notifyDataSetChanged()
    }

    override val layout: Int
        get() = R.layout.fragment_search

    override fun setupListeners() {
        viewBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                selectedQuery = query!!
                viewModel.getSearchResults(SearchRequest(selectedQuery))
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onProductClicked(product: Product) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToProductDetailsFragment(
                product
            )
        )
    }

    override fun onProductAdded(product: Product) {
        viewModel.addToCart(
            AddItemToCartRequest(
                viewModel.getUser()!!.id!!,
                product.productId,
                product.quantity
            )
        )
    }

    override fun onPlusClicked(product: Product, quantity: Int) {
        viewModel.updateQuantity(
            AddItemToCartRequest(viewModel.getUser()!!.id!!, product.productId, product.quantity)
        )
    }

    override fun onMinusClicked(product: Product, quantity: Int) {
        viewModel.updateQuantity(
            AddItemToCartRequest(
                viewModel.getUser()!!.id!!,
                product.productId,
                product.quantity
            )
        )

    }

    override fun onProductRemoved(product: Product) {
        viewModel.removeItemFromCart(
            RemoveItemFromCartRequest(
                viewModel.getUser()!!.id,
                product.productId
            )
        )
    }

    override fun onProductLikeClicked(product: Product) {
        viewModel.addToFavorites(AddToFavoritesRequest(viewModel.getUser()!!.id, product.productId))
    }

    override fun onProductDislikeClicked(product: Product) {
        viewModel.removeFromFavorites(
            AddToFavoritesRequest(
                viewModel.getUser()!!.id!!,
                product.productId
            )
        )
    }

}
