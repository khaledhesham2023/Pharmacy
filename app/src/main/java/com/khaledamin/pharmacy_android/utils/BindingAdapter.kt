package com.khaledamin.pharmacy_android.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.khaledamin.pharmacy_android.R


@BindingAdapter("imgUrl")
fun bindImageViewToView(imageView: ImageView, url:String?){
    Glide.with(imageView.context).load(url).placeholder(R.drawable.ic_logo).into(imageView)
}

@BindingAdapter("showDefault")
fun showDefaultTapByAddressStatus(textView: TextView,status:Boolean){
    if (status){
        textView.visibility = View.VISIBLE
    } else {
        textView.visibility = View.GONE
    }
}