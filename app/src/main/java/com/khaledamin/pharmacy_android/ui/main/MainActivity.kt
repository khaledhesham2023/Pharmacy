package com.khaledamin.pharmacy_android.ui.main

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.khaledamin.pharmacy_android.NavigationDirections
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ActivityMainBinding
import com.khaledamin.pharmacy_android.ui.authentication.login.LoginActivity
import com.khaledamin.pharmacy_android.ui.base.BaseActivity
import com.khaledamin.pharmacy_android.ui.base.BaseActivityWithViewModel
import com.khaledamin.pharmacy_android.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivityWithViewModel<ActivityMainBinding, MainViewModel>() {
    override val layout: Int
        get() = R.layout.activity_main

    private lateinit var navController: NavController
    private lateinit var appMusic: MediaPlayer
    private lateinit var cash: MediaPlayer
    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appMusic = MediaPlayer.create(this, R.raw.main)
        cash = MediaPlayer.create(this, R.raw.cash)
        navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment).findNavController()
        val appBarConfiguration = AppBarConfiguration.Builder(R.id.categoriesFragment).build()

        setSupportActionBar(viewBinding.toolbar)


        setupActionBarWithNavController(navController, appBarConfiguration)

        viewBinding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.accountFragment || destination.id == R.id.addressesFragment || destination.id == R.id.addAddressFragment ||
                destination.id == R.id.mapsFragment || destination.id == R.id.productsFragment || destination.id == R.id.productDetailsFragment
                || destination.id == R.id.ordersFragment || destination.id == R.id.orderDetailsFragment || destination.id == R.id.orderCreatedFragment
                || destination.id == R.id.editAccountFragment
                || destination.id == R.id.phoneEntryFragment
                || destination.id == R.id.verificationFragment
                || destination.id == R.id.changePasswordFragment
                || destination.id == R.id.notificationsFragment
                || destination.id == R.id.searchFragment
                || destination.id == R.id.bagFragment
            ) {
                viewBinding.bottomNavView.visibility = View.GONE
            } else {
                viewBinding.bottomNavView.visibility = View.VISIBLE
            }
            if (destination.id == R.id.orderCreatedFragment) {
                viewBinding.toolbar.visibility = View.GONE
            } else {
                viewBinding.toolbar.visibility = View.VISIBLE
            }
            if (destination.id == R.id.bagFragment) {
                viewBinding.bottomNavView.removeBadge(R.id.bagFragment)

            } else {
                viewBinding.bottomNavView.getOrCreateBadge(R.id.bagFragment).number =
                    viewModel.cartItemsCount.value!!
            }
            if (destination.id == R.id.categoriesFragment) {
                if (viewModel.getUser() != null){
                    viewModel.getCartItems(viewModel.getUser()!!.id!!)
                }
            }
            if (destination.id == R.id.orderCreatedFragment) {
                cash.start()
                cash.setOnCompletionListener {
                    cash.stop()
                    cash = MediaPlayer.create(this, R.raw.cash)
                }
            }
            if (destination.id == R.id.bagFragment){
                if (viewModel.getUser() == null){
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                }
            }
            if(destination.id == R.id.productsFragment){
                viewBinding.fab.visibility = View.VISIBLE
            } else {
                viewBinding.fab.visibility = View.GONE
            }
        }
    }

    override fun setupObservers() {
        viewModel.getCartItemsLiveData.observe(this, Observer {
            when (it) {
                is ViewState.Loading -> loadingDialog.show()
                is ViewState.Success -> {
                    viewModel.cartItemsCount.value = it.data.size
                    loadingDialog.dismiss()

                }

                is ViewState.Error -> {
                    loadingDialog.dismiss()
                }
            }
        })
        viewModel.isBadgeShown.observe(this, Observer {
            if (it && viewModel.cartItemsCount.value!! > 0) {
                viewBinding.bottomNavView.getOrCreateBadge(R.id.bagFragment).number =
                    viewModel.cartItemsCount.value!!

            } else {
                viewBinding.bottomNavView.removeBadge(R.id.bagFragment)
            }
        })

        viewModel.cartItemsCount.observe(this, Observer {
            if (viewModel.isBadgeShown.value!! && it > 0) {
                viewBinding.bottomNavView.getOrCreateBadge(R.id.bagFragment).number = it
            } else {
                viewBinding.bottomNavView.removeBadge(R.id.bagFragment)
            }
        })
    }

    override fun setupListeners() {
    viewBinding.fab.setOnClickListener {
        navController.navigate(NavigationDirections.actionGlobalBagFragment())
    }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onStop() {
        super.onStop()
        appMusic.stop()
        appMusic = MediaPlayer.create(this,R.raw.main)
    }

    override fun onPause() {
        super.onPause()
        appMusic.pause()
    }

    override fun onResume() {
        super.onResume()
//        appMusic.start()
//        appMusic.setOnCompletionListener {
//            appMusic.start()
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.notification -> {
                viewBinding.bottomNavView.removeBadge(R.id.notification)
                navController.navigate(NavigationDirections.actionGlobalNotificationsFragment())
            }

            R.id.search -> {
                navController.navigate(NavigationDirections.actionGlobalSearchFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }

}