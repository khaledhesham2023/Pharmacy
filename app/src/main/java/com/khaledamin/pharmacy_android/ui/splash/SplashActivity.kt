package com.khaledamin.pharmacy_android.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ActivitySplashBinding
import com.khaledamin.pharmacy_android.ui.authentication.login.LoginActivity
import com.khaledamin.pharmacy_android.ui.base.BaseActivity
import com.khaledamin.pharmacy_android.ui.base.BaseActivityWithViewModel
import com.khaledamin.pharmacy_android.ui.main.MainActivity
import com.khaledamin.pharmacy_android.utils.DisplayManager.setLocale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivityWithViewModel<ActivitySplashBinding,SplashViewModel>() {
    override val layout: Int
        get() = R.layout.activity_splash
    override val viewModelClass: Class<SplashViewModel>
        get() = SplashViewModel::class.java

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.getLanguage() == "" || viewModel.getLanguage() == null){
            setLocale(this,"en")
            viewModel.saveLanguage("en")
        } else {
            setLocale(this,viewModel.getLanguage()!!)
            viewModel.saveLanguage(viewModel.getLanguage()!!)
        }
        Handler.createAsync(Looper.getMainLooper()).postDelayed(Runnable {
            if (viewModel.isLoggedIn()){
                startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        },3000L)
    }

    override fun setupObservers() {
//        TODO("Not yet implemented")
    }

    override fun setupListeners() {
//        TODO("Not yet implemented")
    }

}