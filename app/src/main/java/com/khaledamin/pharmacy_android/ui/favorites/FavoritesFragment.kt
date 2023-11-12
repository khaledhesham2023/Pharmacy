package com.khaledamin.pharmacy_android.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentFavoritesBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.ui.model.Product
import com.khaledamin.pharmacy_android.ui.model.requests.AddToFavoritesRequest
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragmentWithViewModel<FragmentFavoritesBinding, FavoritesViewModel>(),FavoritesCallback {

    private lateinit var adapter: FavoritesAdapter

    override val viewModelClass: Class<FavoritesViewModel>
        get() = FavoritesViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FavoritesAdapter(ArrayList(),this)
        viewBinding.favoritesList.adapter = adapter
        viewBinding.favoritesList.layoutManager = GridLayoutManager(requireContext(),2)
        viewModel.getUserFavorites(viewModel.getUser()!!.id!!)
    }

    override fun setupObservers() {
        viewModel.getUserFavoritesLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is ViewState.Loading -> {
                    loadingDialog.show()
                }
                is ViewState.Success -> {
                    adapter.updateDataSet(it.data)
                    loadingDialog.dismiss()
                }
                is ViewState.Error -> {
                    showErrorAlertDialog(requireContext(),R.string.error,it.message,R.string.retry,R.string.cancel){
                        _,_->
                        viewModel.getUserFavorites(viewModel.getUser()!!.id!!)
                    }
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.removeFromFavoritesLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is ViewState.Loading -> {
                    loadingDialog.show()
                }
                is ViewState.Success -> {
                    Toast.makeText(requireContext(),it.data.message,Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                }
                is ViewState.Error -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.addToFavoritesLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is ViewState.Loading -> {
                    loadingDialog.show()
                }
                is ViewState.Success -> {
                    Toast.makeText(requireContext(),it.data.message,Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                }
                is ViewState.Error -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                }
            }
        })
    }

    override val layout: Int
        get() = R.layout.fragment_favorites

    override fun setupListeners() {
//        TODO("Not yet implemented")
    }

    override fun onFavoriteProductClicked(product: Product) {
        findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToProductDetailsFragment(product))
    }

    override fun onLikeIconClicked(product: Product) {
        if (product.isLiked){
            viewModel.removeFromFavorites(AddToFavoritesRequest(viewModel.getUser()!!.id!!,product.productId))
        } else {
            viewModel.addToFavorites(AddToFavoritesRequest(viewModel.getUser()!!.id!!,product.productId))
        }
    }

}