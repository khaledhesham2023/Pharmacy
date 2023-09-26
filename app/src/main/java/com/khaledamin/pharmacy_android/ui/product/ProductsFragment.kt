package com.khaledamin.pharmacy_android.ui.product

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentProductsBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.ui.model.Category
import com.khaledamin.pharmacy_android.ui.model.CategoryItem
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.SubCategory
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : BaseFragmentWithViewModel<FragmentProductsBinding, ProductsViewModel>(),
    CategoryItemCallback, SubcategoryCallback, ProductCallback {

    private lateinit var category: Category
    private lateinit var categoryAdapter: CategoryAdapter
    private var position = 0
    private lateinit var subcategoryAdapter: SubcategoryAdapter
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var data:List<Product>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        data = ArrayList()
        super.onViewCreated(view, savedInstanceState)
        checkData(data)
        categoryAdapter = CategoryAdapter(ArrayList(), this)
        subcategoryAdapter = SubcategoryAdapter(ArrayList(), this)
        productsAdapter = ProductsAdapter(data, this)
        category = ProductsFragmentArgs.fromBundle(requireArguments()).category
        viewBinding.categories.adapter = categoryAdapter
        viewBinding.categories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        position = ProductsFragmentArgs.fromBundle(requireArguments()).position
        viewBinding.subcategories.adapter = subcategoryAdapter
        viewBinding.subcategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewBinding.products.adapter = productsAdapter
        viewBinding.products.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.getCategoryContents()
    }

    private fun checkData(data: List<Product>) {
        if(data.isEmpty()){
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
                    categoryAdapter.updateDataSet(it.data.categories)
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
                        viewModel.getCategoryContents()

                    }
                    loadingDialog.dismiss()
                }
            }
        })
    }

    override val layout: Int
        get() = R.layout.fragment_products

    override fun setupListeners() {
//        TODO("Not yet implemented")
    }

    override fun onCategoryItemClicked(categoryItem: CategoryItem, position: Int) {
        viewBinding.categories.scrollToPosition(position)
        productsAdapter.updateDataSet(ArrayList())
        category.categoryId = categoryItem.id
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
        findNavController().navigate(ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment(product,category.categoryId!!))
    }


}