package com.khaledamin.pharmacy_android.ui.bag

import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentBagBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BagFragment : BaseFragmentWithViewModel<FragmentBagBinding,BagViewModel>() {
    override val viewModelClass: Class<BagViewModel>
        get() = BagViewModel::class.java

    override fun setupListeners() {
//        TODO("Not yet implemented")
    }

    override fun setupObservers() {
//        TODO("Not yet implemented")
    }

    override val layout: Int
        get() = R.layout.fragment_bag

}