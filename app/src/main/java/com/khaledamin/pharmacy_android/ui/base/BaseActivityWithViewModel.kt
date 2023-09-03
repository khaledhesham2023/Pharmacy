package com.khaledamin.pharmacy_android.ui.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivityWithViewModel<VB: ViewDataBinding, VM: ViewModel>: BaseActivity<VB>() {

    abstract val viewModelClass: Class<VM>

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[viewModelClass]
        setupObservers()
    }

    abstract fun setupObservers()

}