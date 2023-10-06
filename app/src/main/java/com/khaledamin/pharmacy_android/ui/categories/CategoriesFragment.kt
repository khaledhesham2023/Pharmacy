package com.khaledamin.pharmacy_android.ui.categories

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentCategoriesBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment :
    BaseFragmentWithViewModel<FragmentCategoriesBinding, CategoriesViewModel>(), CategoryCallback {

    private lateinit var categoriesAdapter: CategoriesAdapter
//    private lateinit var categoriesProductsSharedViewModel: CategoriesProductsSharedViewModel

    override val viewModelClass: Class<CategoriesViewModel>
        get() = CategoriesViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        categoriesProductsSharedViewModel =
//            ViewModelProvider(requireActivity())[CategoriesProductsSharedViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoriesAdapter = CategoriesAdapter(ArrayList(), this)
        viewBinding.categoriesList.adapter = categoriesAdapter
        viewBinding.categoriesList.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.getCatalog(viewModel.getLanguage()!!)
    }

    override fun setupListeners() {
//        TODO("Not yet implemented")
    }

    override fun setupObservers() {
        viewModel.catalogLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    loadingDialog.show()
                }

                is ViewState.Success -> {
                    categoriesAdapter.updateDataSet(it.data.categories!!)
                    val slides: ArrayList<SlideModel> = ArrayList()
                    for (slide in it.data.sliders!!) {
                        slides.add(SlideModel(slide.sliderImage, slide.sliderTitle))
                    }
                    viewBinding.sliderView.setImageList(slides)
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
                        viewModel.getCatalog(viewModel.getLanguage()!!)
                    }
                    loadingDialog.dismiss()
                }
            }
        })
    }

    override val layout: Int
        get() = R.layout.fragment_categories

    override fun onCategoryClicked(categoryId:Long) {
//        categoriesProductsSharedViewModel.categoryProductsLiveData.value!!.category = category
//        categoriesProductsSharedViewModel.categoryProductsLiveData.value!!.position = position
        findNavController().navigate(CategoriesFragmentDirections.actionCategoriesFragmentToProductsFragment(categoryId))
    }


}