package com.khaledamin.pharmacy_android.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.khaledamin.pharmacy_android.R

abstract class BaseAdapter<T, VB : ViewDataBinding, VH:RecyclerView.ViewHolder>(protected var data:List<T>) : Adapter<VH>() {

    abstract val layout : Int

    protected open fun getItemViewBinding(parent: ViewGroup): VB{
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout,parent,false)
    }

    open fun updateDataSet(data: List<T>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size
}