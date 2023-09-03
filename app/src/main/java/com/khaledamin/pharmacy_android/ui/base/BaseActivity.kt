package com.khaledamin.pharmacy_android.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.khaledamin.pharmacy_android.loading.LoadingDialog

abstract class BaseActivity<VB: ViewDataBinding>: AppCompatActivity() {

    protected lateinit var viewBinding: VB

    abstract val layout:Int

    protected lateinit var loadingDialog:LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = DataBindingUtil.setContentView(this,layout)
        loadingDialog = LoadingDialog(this)
        setupListeners()
    }
    abstract fun setupListeners()


}