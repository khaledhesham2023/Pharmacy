package com.khaledamin.pharmacy_android.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ActivityMainBinding
import com.khaledamin.pharmacy_android.ui.authentication.login.LoginActivity
import com.khaledamin.pharmacy_android.ui.base.BaseActivity
import com.khaledamin.pharmacy_android.ui.base.BaseActivityWithViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivityWithViewModel<ActivityMainBinding, MainViewModel>() {
    override val layout: Int
        get() = R.layout.activity_main

    private lateinit var navController: NavController
    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment).findNavController()
        val appBarConfiguration = AppBarConfiguration.Builder(R.id.categoriesFragment).build()

        setSupportActionBar(viewBinding.toolbar)


        setupActionBarWithNavController(navController, appBarConfiguration)

        viewBinding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.accountFragment || destination.id == R.id.addressesFragment || destination.id == R.id.addAddressFragment ||
            destination.id == R.id.mapsFragment) {
                viewBinding.bottomNavView.visibility = View.GONE
            } else {
                viewBinding.bottomNavView.visibility = View.VISIBLE
            }
        }
    }

    override fun setupObservers() {
//        TODO("Not yet implemented")
    }

    override fun setupListeners() {
//        TODO("Not yet implemented")
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}