package com.khaledamin.pharmacy_android.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentAccountBinding
import com.khaledamin.pharmacy_android.ui.authentication.login.LoginActivity
import com.khaledamin.pharmacy_android.ui.base.BaseFragment
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.ui.main.MainActivity
import com.khaledamin.pharmacy_android.utils.DisplayManager.setLocale
import com.khaledamin.pharmacy_android.utils.DisplayManager.showAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : BaseFragmentWithViewModel<FragmentAccountBinding, AccountViewModel>() {
    override val layout: Int
        get() = R.layout.fragment_account
    override val viewModelClass: Class<AccountViewModel>
        get() = AccountViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.isLoggedIn()) {
            viewBinding.name.text =
                "${viewModel.getUser()!!.firstName} " + "${viewModel.getUser()!!.lastname}"
            viewBinding.email.text = "${viewModel.getUser()!!.email}"
        } else {
            startActivity(Intent(requireActivity(),LoginActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun setupListeners() {
        viewBinding.logoutLayout.setOnClickListener {
            showAlertDialog(
                requireContext(),
                R.string.confirmation,
                R.string.confirm_logout,
                R.string.log_out,
                R.string.cancel
            ) {_,_ ->
                viewModel.setLoggedIn(false)
                viewModel.saveUser(null)
                viewModel.saveBearerToken(null)
                viewModel.saveUserName(null)
                viewModel.savePhone(null)
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }
        viewBinding.languageLayout.setOnClickListener {
            showAlertDialog(requireContext(),R.string.confirmation,R.string.confirm_language,R.string.confirm,R.string.cancel) { _, _ ->
                when (viewModel.getLanguage()) {
                    "en" -> {
                        setLocale(requireContext(),"ar")
                        viewModel.saveLanguage("ar")
                        startActivity(Intent(requireActivity(),MainActivity::class.java))
                        requireActivity().finish()
                    }
                    "ar" -> {
                        setLocale(requireContext(),"en")
                        viewModel.saveLanguage("en")
                        startActivity(Intent(requireActivity(),MainActivity::class.java))
                        requireActivity().finish()
                    }
                }
            }
        }
        viewBinding.locationGroup.setOnClickListener {
            findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToAddressesFragment())
        }
    }

    override fun setupObservers() {
//        TODO("Not yet implemented")
    }

}