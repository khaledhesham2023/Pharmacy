package com.khaledamin.pharmacy_android.ui.product

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentProductsBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
//import com.khaledamin.pharmacy_android.ui.categories.CategoriesProductsSharedViewModel
import com.khaledamin.pharmacy_android.ui.model.Category
import com.khaledamin.pharmacy_android.ui.model.CategoryItem
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.SubCategory
import com.khaledamin.pharmacy_android.ui.model.requests.AddItemToCartRequest
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
//    private lateinit var categoriesProductsSharedViewModel: CategoriesProductsSharedViewModel
    private lateinit var subcategories: List<SubCategory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        categoriesProductsSharedViewModel =
//            ViewModelProvider(requireActivity())[CategoriesProductsSharedViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        data = ArrayList()
//        subcategories = ArrayList()
        super.onViewCreated(view, savedInstanceState)
        categoryId = ProductsFragmentArgs.fromBundle(requireArguments()).categoryId
        checkData(data)
        categoryAdapter = CategoryAdapter(ArrayList(), this)
        subcategoryAdapter = SubcategoryAdapter(ArrayList(), this)
        productsAdapter = ProductsAdapter(data, this)
        viewBinding.categories.adapter = categoryAdapter
        viewBinding.categories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        position = ProductsFragmentArgs.fromBundle(requireArguments()).position
        viewBinding.subcategories.adapter = subcategoryAdapter
        viewBinding.subcategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewBinding.products.adapter = productsAdapter
        viewBinding.products.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.getCategoryContents(viewModel.getLanguage()!!)
        viewModel.getUserCartItems(viewModel.getUser()!!.id!!)
    }

    private fun checkData(data: List<Product>) {
        if (data.isEmpty()) {
            viewBinding.emptyView.visibility = View.VISIBLE
            viewBinding.products.visibility = View.GONE
        } else {
            viewBinding.emptyView.visibility = View.GONE
            viewBinding.products.visibility = View.VISIBLE
        }

    }

    override val viewModelClass: Class<ProductsViewModel>
        get() = ProductsViewModel::class.java

    override fun setupObservers() {
        viewModel.getCategoryContentsResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    selectDefaultCategory(it.data.categories)
                    categoryAdapter.updateDataSet(it.data.categories)
//                    subcategories = it.data.categories[position].subCategories
//                    onCategoryItemClicked(CategoryItem(category.categoryId!!,category.categoryTitle!!,it.data.categories[position].subCategories),position)
                    loadingDialog.dismiss()
                }

                is ViewState.Error -> {
                    showErrorAlertDialog(
                        requireContext(),
                        R.string.error,
                        getString(R.string.error_loading_categories),
                        R.string.retry,
                        R.string.cancel
                    ) { _, _ ->
                        viewModel.getCategoryContents(viewModel.getLanguage()!!)

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

        viewModel.getUserCartItemsLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
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
//        categoriesProductsSharedViewModel.categoryProductsLiveData.observe(viewLifecycleOwner,
//            Observer {
//                category = it.category
//                position = it.position
//            })
    }

    private fun selectDefaultCategory(data:List<CategoryItem>) {
        for (category in data){
            if(category.id == categoryId){
                Log.i("TAGG","category ${data.indexOf(category)} checked")
                category.isSelected = true
                onCategoryItemClicked(category,data.indexOf(category))
                break
            }
        }
    }

    override val layout: Int
        get() = R.layout.fragment_products

    override fun setupListeners() {
//        TODO("Not yet implemented")
    }

    override fun onCategoryItemClicked(categoryItem: CategoryItem, position: Int) {
        viewBinding.categories.scrollToPosition(position)
        productsAdapter.updateDataSet(ArrayList())
//        category.categoryId = categoryItem.id
        if (categoryItem.subCategories.isEmpty()) {
            data = ArrayList()
            checkData(data)
            productsAdapter.updateDataSet(data)
            subcategoryAdapter.updateDataSet(ArrayList())
        } else {
            subcategoryAdapter.updateDataSet(categoryItem.subCategories)
        }
    }


    override fun onSubcategoryClicked(subCategory: SubCategory, position: Int) {
        viewBinding.subcategories.scrollToPosition(position)
        if (subCategory.products.isEmpty()) {
            data = ArrayList()
            checkData(data)
            productsAdapter.updateDataSet(data)
        } else {
            data = subCategory.products
            checkData(data)
            productsAdapter.updateDataSet(data)
        }
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
}