package com.khaledamin.pharmacy_android.utils

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.khaledamin.pharmacy_android.datastore.local.repo.Repo
import com.khaledamin.pharmacy_android.ui.model.User
import com.khaledamin.pharmacy_android.ui.model.requests.LoginRequest
import com.khaledamin.pharmacy_android.ui.model.responses.LoginResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

class ApiRequestManager
@Inject
constructor(
    @ApplicationContext
    val context: Context,
    val repo: Repo,
) {

    fun <T> requestApi(request: Single<T>, liveData: MutableLiveData<ViewState<T>>) {
        if (isInternetConnected(context)) {
            liveData.value = ViewState.Loading
            request.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(object :
                    SingleObserver<T> {
                    override fun onSubscribe(d: io.reactivex.disposables.Disposable?) {
//                    TODO("Not yet implemented")
                    }


                    override fun onError(e: Throwable) {
                        if (e is HttpException && (e.code() == 401 || e.code() == 403)) {
                            refreshToken(object : Retryable {
                                override fun retry() {
                                    requestApi(request, liveData)
                                }
                            }, liveData)
                        } else {
                            liveData.value =
                                ViewState.Error(e.message!!)
                        }
                    }

                    override fun onSuccess(t: T) {
                        liveData.value = ViewState.Success(t, "Success")
                    }

                })
        }
    }

    private fun <T> refreshToken(retryable: Retryable, liveData: MutableLiveData<ViewState<T>>) {
        repo.login(LoginRequest(repo.getUsername(), repo.getPassword()), repo.getLanguage()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<LoginResponse> {
                override fun onSuccess(t: LoginResponse) {
                    if (t.status) {
                        repo.saveUser(t.user)
                        repo.saveBearerToken(t.user!!.token!!)
                        retryable.retry()
                    } else {
                        liveData.value = ViewState.Error(t.message!!)
                    }
                }

                override fun onSubscribe(d: io.reactivex.disposables.Disposable?) {
//                    TODO("Not yet implemented")
                }

                override fun onError(e: Throwable) {
                    if (e is HttpException) {
                        liveData.value = ViewState.Error(e.response()!!.errorBody()!!.string())
                    } else {
                        liveData.value = ViewState.Error(e.message ?: "Error")
                    }
                }
            })
    }

    interface Retryable {
        fun retry()
    }
}