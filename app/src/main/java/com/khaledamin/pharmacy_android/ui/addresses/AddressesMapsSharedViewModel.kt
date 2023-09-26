package com.khaledamin.pharmacy_android.ui.addresses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressesMapsSharedViewModel @Inject constructor() : ViewModel() {
    var addressName = MutableLiveData<String>()
}