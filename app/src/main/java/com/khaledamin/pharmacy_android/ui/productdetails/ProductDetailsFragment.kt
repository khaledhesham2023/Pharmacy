package com.khaledamin.pharmacy_android.ui.productdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentProductDetailsBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragment
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.ui.model.CartItem
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.requests.AddItemToCartRequest
import com.khaledamin.pharmacy_android.ui.model.requests.AddToFavoritesRequest
import com.khaledamin.pharmacy_android.ui.model.requests.GetRelatedProductsRequest
import com.khaledamin.pharmacy_android.ui.model.requests.RemoveItemFromCartRequest
import com.khaledamin.pharmacy_android.ui.product.ProductCallback
import com.khaledamin.pharmacy_android.ui.product.ProductsAdapter
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment :
    BaseFragmentWithViewModel<FragmentProductDetailsBinding, ProductDetailsViewModel>(),
    ProductCallback {

    private lateinit var product: Product
    private lateinit var productsAdapter: ProductsAdapter
    private var categoryId: Long = 0
    private var page = 0
    private var isLiked = false
    private var productQuantity = 0
    private lateinit var likedProducts:List<Product>
    override val viewModelClass: Class<ProductDetailsViewModel>
        get() = ProductDetailsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product = ProductDetailsFragmentArgs.fromBundle(requireArguments()).product
        isLiked = product.isLiked
        productsAdapter = ProductsAdapter(ArrayList(), this)
        viewBinding.otherProductsList.adapter = productsAdapter
        viewBinding.otherProductsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewBinding.product = product
        viewBinding.ratingBar.rating = product.rate!!.toFloat()
        viewModel.getRelatedProducts(
            GetRelatedProductsRequest(page, product.productId)
        )
        if (productQuantity == 0){
            viewBinding.quantityGroup.visibility = View.GONE
            viewBinding.addCartButton.visibility = View.VISIBLE
        }
        viewModel.getCartItems(viewModel.getUser()!!.id!!)
        viewModel.getUserFavorites(viewModel.getUser()!!.id!!)

    }

//    override fun onResume() {
//        super.onResume()
//        if(product.isLiked){
//            viewBinding.likeIcon.setImageResource(R.drawable.ic_heart_active)
//        } else {
//            viewBinding.likeIcon.setImageResource(R.drawable.ic_heart_inactive)
//        }
//    }

    private fun configureProduct(cartItems: List<CartItem>) {
        for (cartItem in cartItems) {
            if (product.productId == cartItem.product!!.productId) {
                productQuantity = cartItem.quantity!!
                if (cartItem.quantity!! < 1) {
                    viewBinding.addCartButton.visibility = View.VISIBLE
                    viewBinding.quantityGroup.visibility = View.GONE
                } else {
                    viewBinding.quantityGroup.visibility = View.VISIBLE
                    viewBinding.addCartButton.visibility = View.GONE
                    viewBinding.quantity.text = cartItem.quantity.toString()
                }
                return
            } else {
                continue
            }
        }
    }

    override fun setupObservers() {
        viewModel.getRelatedProductsLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    productsAdapter.updateDataSet(it.data.products)
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
                        viewModel.getRelatedProducts(
                            GetRelatedProductsRequest(page, product.productId!!)
                        )
                    }
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.addToFavorites.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> loadingDialog.show()
                is ViewState.Success -> {
                    Snackbar.make(
                        requireContext(),
                        viewBinding.root,
                        it.data.message!!,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    viewBinding.product = product
                    viewBinding.likeIcon.setImageResource(R.drawable.ic_heart_active)
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
                    viewBinding.product = product
                    viewBinding.addCartButton.visibility = View.GONE
                    viewBinding.quantityGroup.visibility = View.VISIBLE
                    viewBinding.quantity.text = productQuantity.toString()
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
                    viewBinding.product = product
                    viewBinding.quantity.text = productQuantity.toString()

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
                    viewBinding.product = product
                    viewBinding.quantityGroup.visibility = View.GONE
                    viewBinding.addCartButton.visibility = View.VISIBLE
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
                    viewBinding.product = product
                    viewBinding.likeIcon.setImageResource(R.drawable.ic_heart_inactive)
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
        viewModel.getCartItemsLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    configureProduct(it.data)
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
                        viewModel.getCartItems(viewModel.getUser()!!.id!!)
                    }
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.getUserFavoritesLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }
                is ViewState.Success -> {
                    likedProducts = it.data
                    configureLikedProducts(likedProducts)
                    loadingDialog.dismiss()
                }
                is ViewState.Error -> {
                    loadingDialog.dismiss()
                }
            }
        })

    }

    private fun configureLikedProducts(data:List<Product>) {
        isLiked = data.contains(product)
        if (isLiked){
            viewBinding.likeIcon.setImageResource(R.drawable.ic_heart_active)
        } else {
            viewBinding.likeIcon.setImageResource(R.drawable.ic_heart_inactive)
        }
    }

    override val layout: Int
        get() = R.layout.fragment_product_details

    override fun setupListeners() {
        viewBinding.addCartButton.setOnClickListener {
            onProductAdded(product)
        }
        viewBinding.plus.setOnClickListener {
            onPlusClicked(product, product.quantity + 1)
        }
        viewBinding.minus.setOnClickListener {
            onMinusClicked(product, product.quantity - 1)
        }
        viewBinding.likeIcon.setOnClickListener {
            if (product.isLiked) {
                onProductDislikeClicked(product)
            } else {
                onProductLikeClicked(product)
            }
        }
    }

    override fun onProductClicked(product: Product) {
        findNavController().navigate(
            ProductDetailsFragmentDirections.actionProductDetailsFragmentSelf(
                product
            )
        )
    }

    override fun onProductAdded(product: Product) {
        productQuantity = 1
        viewModel.addToCart(
            AddItemToCartRequest(
                viewModel.getUser()!!.id!!,
                product.productId,
                productQuantity
            )
        )
    }

    override fun onPlusClicked(product: Product, quantity: Int) {
        productQuantity ++
        viewModel.updateQuantity(
            AddItemToCartRequest(
                viewModel.getUser()!!.id!!,
                product.productId,
                productQuantity
            )
        )
    }

    override fun onMinusClicked(product: Product, quantity: Int) {
        productQuantity--
        if (productQuantity == 0) {
            onProductRemoved(product)
        } else {
            viewModel.updateQuantity(
                AddItemToCartRequest(
                    viewModel.getUser()!!.id!!,
                    product.productId,
                    productQuantity
                )
            )
        }
    }

    override fun onProductRemoved(product: Product) {
        viewModel.removeItemFromCart(
            RemoveItemFromCartRequest(viewModel.getUser()!!.id!!, product.productId)
        )
    }

    override fun onProductLikeClicked(product: Product) {
        viewModel.addToFavorites(
            AddToFavoritesRequest(
                viewModel.getUser()!!.id!!,
                product.productId
            )
        )
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