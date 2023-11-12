package com.khaledamin.pharmacy_android.ui.product

//import com.khaledamin.pharmacy_android.ui.categories.CategoriesProductsSharedViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentProductsBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.ui.model.CartItem
import com.khaledamin.pharmacy_android.ui.model.Category
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.SubCategory
import com.khaledamin.pharmacy_android.ui.model.requests.AddItemToCartRequest
import com.khaledamin.pharmacy_android.ui.model.requests.AddToFavoritesRequest
import com.khaledamin.pharmacy_android.ui.model.requests.FilterRequest
import com.khaledamin.pharmacy_android.ui.model.requests.ProductsRequest
import com.khaledamin.pharmacy_android.ui.model.requests.RemoveItemFromCartRequest
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : BaseFragmentWithViewModel<FragmentProductsBinding, ProductsViewModel>(),
    CategoryItemCallback, SubcategoryCallback, ProductCallback {

    private var categoryId: Long = -1
    private lateinit var categoryAdapter: CategoryAdapter
    private var position = 0
    private lateinit var subcategoryAdapter: SubcategoryAdapter
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var data: List<Product>
    private lateinit var subcategories: List<SubCategory>
    private lateinit var products: List<Product>
    private lateinit var cartProducts: List<CartItem>
    private var page = 0
    private var maxItems = 5
    private var subcategoryId: Long = -1L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        data = ArrayList()
        checkCurrentPage()
        super.onViewCreated(view, savedInstanceState)
        categoryId = ProductsFragmentArgs.fromBundle(requireArguments()).categoryId
        categoryAdapter = CategoryAdapter(ArrayList(), this)
        subcategoryAdapter = SubcategoryAdapter(ArrayList(), this)
        productsAdapter = ProductsAdapter(data, this)
        viewBinding.categories.adapter = categoryAdapter
        viewBinding.categories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewBinding.subcategories.adapter = subcategoryAdapter
        viewBinding.subcategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewBinding.products.adapter = productsAdapter
        viewBinding.products.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.getCategories()
        viewBinding.page.text = getPageUI().toString()
        checkCurrentPage()
    }

    private fun checkCurrentPage() {
        if (page < 1) {
            viewBinding.left.visibility = View.GONE
        } else {
            viewBinding.left.visibility = View.VISIBLE
        }
        if (data.size < maxItems || data.isEmpty()) {
            viewBinding.right.visibility = View.GONE
        } else {
            viewBinding.right.visibility = View.VISIBLE
        }
    }


//    private fun checkData(data: List<Product>) {
//        if (data.isEmpty()) {
//            viewBinding.emptyView.visibility = View.VISIBLE
//            viewBinding.products.visibility = View.GONE
//        } else {
//            viewBinding.emptyView.visibility = View.GONE
//            viewBinding.products.visibility = View.VISIBLE
//        }
//
//    }

    override val viewModelClass: Class<ProductsViewModel>
        get() = ProductsViewModel::class.java

    override fun setupObservers() {
//        viewModel.getCategoryContentsResponse.observe(viewLifecycleOwner, Observer {
//            when (it) {
//                is ViewState.Loading -> {
//                    loadingDialog.show()
//                }
//
//                is ViewState.Success -> {
//                    selectDefaultCategory(it.data.categories)
//                    categoryAdapter.updateDataSet(it.data.categories)
//                    viewModel.getUserCartItems(viewModel.getUser()!!.id!!)
//                    viewModel.getUserFavorites(viewModel.getUser()!!.id!!)
//                    loadingDialog.dismiss()
//                }
//
//                is ViewState.Error -> {
//                    showErrorAlertDialog(
//                        requireContext(),
//                        R.string.error,
//                        getString(R.string.error_loading_categories),
//                        R.string.retry,
//                        R.string.cancel
//                    ) { _, _ ->
//                        viewModel.getCategoryContents(viewModel.getLanguage()!!)
//
//                    }
//                    loadingDialog.dismiss()
//                }
//            }
//        })
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

        viewModel.getUserCartItemsLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    cartProducts = it.data
                    modifyQuantities(cartProducts)
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
        viewModel.getUserFavoritesLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    setUserProductsAsFavorites(it.data)
                    loadingDialog.dismiss()
                }

                is ViewState.Error -> {
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.getProductsLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    data = it.data
                    productsAdapter.updateDataSet(data)
                    checkCurrentPage()
                    viewModel.getUserFavorites(viewModel.getUser()!!.id!!)
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
                        viewModel.getProducts(ProductsRequest(categoryId, page))
                    }
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.getCategoriesLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    selectDefaultCategory(it.data)
                    categoryAdapter.updateDataSet(it.data)
                    viewModel.getSubcategoriesByCategory(categoryId)
                    viewModel.getProducts(ProductsRequest(categoryId, page))
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
                        viewModel.getCategories()
                    }
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.getSubcategoryByCategoryIdLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    subcategoryAdapter.updateDataSet(it.data)
                    loadingDialog.dismiss()
                }

                is ViewState.Error -> {
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.getProductsBySubcategoryLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    data = it.data
                    Log.i("TAGG",data.size.toString())
                    productsAdapter.updateDataSet(data)
                    checkCurrentPage()
                    viewModel.getUserFavorites(viewModel.getUser()!!.id!!)
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
                        viewModel.getProductsBySubcategory(FilterRequest(subcategoryId, page))
                    }
                }
            }
        })
    }

    private fun setUserProductsAsFavorites(likedProducts: List<Product>) {
        val products = data
        for (likedProduct in likedProducts) {
            for (product in products) {
                if (product.productId == likedProduct.productId) {
                    product.isLiked = true
                    productsAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun modifyQuantities(cartProducts: List<CartItem>) {
        for (cartProduct in cartProducts) {
            for (product in data) {
                if (cartProduct.product!!.productId == product.productId) {
                    product.quantity = cartProduct.quantity!!
                }
            }
        }
        productsAdapter.notifyDataSetChanged()
    }

    private fun getPageUI(): Int {
        return page + 1
    }
//    private fun getAllProducts(): List<Product> {
//        val products = ArrayList<Product>()
//        for (category in categoryAdapter.categories) {
//            for (subcategory in category.subCategories) {
//                products.addAll(subcategory.products)
//            }
//        }
//        return products
//    }

    private fun selectDefaultCategory(data: List<Category>) {
        for (category in data) {
            if (category.categoryId == categoryId) {
                category.isSelected = true
                categoryId = category.categoryId!!
                categoryAdapter.selectedCategory = category
                onCategoryItemClicked(category, data.indexOf(category))
                break
            }
        }
    }

    override val layout: Int
        get() = R.layout.fragment_products

    override fun setupListeners() {
        viewBinding.left.setOnClickListener {
            if (subcategoryId == -1L) {
                if (page < 1) {
                    viewBinding.page.text = getPageUI().toString()
                } else {
                    page--
                    viewBinding.page.text = getPageUI().toString()
                    viewModel.getProducts(ProductsRequest(categoryId, page))
                }
            } else {
                if (page < 1) {
                    viewBinding.page.text = getPageUI().toString()
                } else {
                    page--
                    viewModel.getProductsBySubcategory(FilterRequest(subcategoryId, page))
                    viewBinding.page.text = getPageUI().toString()
                }
            }

        }
        viewBinding.right.setOnClickListener {
            if (subcategoryId == -1L) {
                page++
                viewModel.getProducts(ProductsRequest(categoryId, page))
                viewBinding.page.text = getPageUI().toString()
                checkCurrentPage()
            } else {
                page++
                viewModel.getProductsBySubcategory(FilterRequest(subcategoryId, page))
                viewBinding.page.text = getPageUI().toString()
                checkCurrentPage()
            }

        }
    }


    override fun onCategoryItemClicked(categoryItem: Category, position: Int) {
        viewBinding.categories.scrollToPosition(position)
        categoryId = categoryItem.categoryId!!
        page = 0
        viewBinding.page.text = getPageUI().toString()
        viewModel.getSubcategoriesByCategory(categoryId)
        viewModel.getProducts(ProductsRequest(categoryId, page))
//        productsAdapter.updateDataSet(ArrayList())
//        if (categoryItem.subCategories.isEmpty()) {
//            data = ArrayList()
//            checkData(data)
//            productsAdapter.updateDataSet(data)
//            subcategoryAdapter.updateDataSet(ArrayList())
//        } else {
//            subcategoryAdapter.updateDataSet(categoryItem.subCategories)
////            data = extractProducts(categoryItem.subCategories)
//            checkData(data)
//            productsAdapter.updateDataSet(data)
//        }
    }

//    private fun extractProducts(subcategories: List<SubCategory>): List<Product> {
//        val products = ArrayList<Product>()
//        for (subcategory in subcategories) {
//            products.addAll(subcategory.products)
//        }
//        return products
//    }


    override fun onSubcategoryClicked(subCategory: SubCategory, position: Int) {
        productsAdapter.updateDataSet(ArrayList())
        subcategoryId = subCategory.subCategoryId!!
        viewBinding.subcategories.scrollToPosition(position)
        page = 0
        viewBinding.page.text = getPageUI().toString()
        checkCurrentPage()
        viewModel.getProductsBySubcategory(FilterRequest(subcategoryId, page))
//        if (subCategory.products.isEmpty()) {
//            data = ArrayList()
//            checkData(data)
//            productsAdapter.updateDataSet(data)
//        } else {
//            data = subCategory.products
//            checkData(data)
//            productsAdapter.updateDataSet(data)
//        }
    }

    override fun onProductClicked(product: Product) {
        findNavController().navigate(
            ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment(
                product
            )
        )
    }

    override fun onProductAdded(product: Product) {
        viewModel.addItemToCart(
            AddItemToCartRequest(
                viewModel.getUser()!!.id!!,
                product.productId,
                1
            )
        )
    }

    override fun onPlusClicked(product: Product, quantity: Int) {
        viewModel.addItemToCart(
            AddItemToCartRequest(
                viewModel.getUser()!!.id!!,
                product.productId,
                quantity
            )
        )
    }

    override fun onMinusClicked(product: Product, quantity: Int) {
        viewModel.updateQuantity(
            AddItemToCartRequest(
                viewModel.getUser()!!.id!!,
                product.productId,
                quantity
            )
        )
    }

    override fun onProductRemoved(product: Product) {
        viewModel.removeItemFromCart(
            RemoveItemFromCartRequest(
                viewModel.getUser()!!.id!!,
                product.productId
            )
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