package com.khaledamin.pharmacy_android.utils

import android.content.Context
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ApiRequestManager
@Inject
constructor(
    @ApplicationContext
    val context: Context) {

    fun <T> requestApi(request: Single<T>, liveData: MutableLiveData<ViewState<T>>) {
        if (isInternetConnected(context)) {
            liveData.value = ViewState.Loading
            request.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(object :
                    SingleObserver<T> {
                    override fun onSubscribe(d: io.reactivex.disposables.Disposable?) {
//            TODO("Not yet implemented")
                    }


                    override fun onError(e: Throwable) {
                        liveData.value = ViewState.Error(e.message!!)
                    }

                    override fun onSuccess(t: T) {
                        liveData.value = ViewState.Success(t, "Success")
                    }

                })


        }
    }
}