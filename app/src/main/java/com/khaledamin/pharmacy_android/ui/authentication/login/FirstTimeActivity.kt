package com.khaledamin.pharmacy_android.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnDragListener
import android.view.View.OnFocusChangeListener
import android.view.View.OnHoverListener
import android.view.View.OnScrollChangeListener
import android.widget.Toast
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.models.SlideModel
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.ActivityFirstTimeBinding
import com.khaledamin.pharmacy_android.ui.base.BaseActivity
import com.khaledamin.pharmacy_android.ui.base.BaseActivityWithViewModel
import com.khaledamin.pharmacy_android.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstTimeActivity : BaseActivityWithViewModel<ActivityFirstTimeBinding,FirstTimeViewModel>() {
    override val layout: Int
        get() = R.layout.activity_first_time
    override val viewModelClass: Class<FirstTimeViewModel>
        get() = FirstTimeViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val infoList =
            arrayListOf<SlideModel>(
                SlideModel(
                    "https://img.freepik.com/premium-vector/pharmacist-says-hello-welcome-customer_659844-185.jpg?w=2000",
                    getString(R.string.welcome)
                ),
                SlideModel(
                    "https://img.freepik.com/free-vector/rx-prescription-flat-illustration_74855-16234.jpg?w=740&t=st=1693247260~exp=1693247860~hmac=b03f4887cea3be2d2aa3ba37ac6be99b16383534b1e61b13815adf9c7af0c959",
                    getString(R.string.start_healthy_journey)
                ),
                SlideModel(
                    "https://img.freepik.com/free-vector/illustration-delivery-service-with-mask_23-2148505038.jpg?w=740&t=st=1693247344~exp=1693247944~hmac=b501aa0a273d7c593c840aa199202a1eb2bf6aebcca04865b777d4f0314852de",
                    getString(R.string.delivery_feature)
                ),
                SlideModel(
                    "https://img.freepik.com/free-vector/pharmacy-paper-bag-medicine_603843-3825.jpg",
                    getString(R.string.find_feature)
                ),
                SlideModel(
                    "https://img.freepik.com/free-vector/physical-pain-injury-flat-composition-with-human-characters-suffering-patient-doctor-inside-smartphone-vector-illustration_1284-80339.jpg?w=740&t=st=1693247377~exp=1693247977~hmac=e2e72ee442c15ddfc06702fc5aeeec83b11024c148114de021c60e53337922c3",
                    getString(R.string.app_feature)
                )
            )
        viewBinding.sliderView.setImageList(infoList)

        viewBinding.sliderView.setItemChangeListener(object :ItemChangeListener{
            override fun onItemChanged(position: Int) {
                viewBinding.next.isEnabled = position == infoList.size-1
            }

        })

        }

    override fun setupObservers() {
//        TODO("Not yet implemented")
    }

    override fun setupListeners() {
        viewBinding.next.setOnClickListener {
        viewModel.setFirstTime(viewModel.getUserName()!!,false)
            startActivity(Intent(this@FirstTimeActivity,MainActivity::class.java))
            finish()
        }
    }
}