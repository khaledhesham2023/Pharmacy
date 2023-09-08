package com.khaledamin.pharmacy_android.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.khaledamin.pharmacy_android.loading.LoadingDialog

abstract class BaseFragment<VB: ViewDataBinding> : Fragment() {

    abstract val layout :Int

    protected lateinit var viewBinding: VB
    protected lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater,layout,container,false)
        loadingDialog = LoadingDialog(requireContext())
        setupListeners()
        return viewBinding.root
    }

    abstract fun setupListeners()
}