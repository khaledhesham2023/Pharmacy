package com.khaledamin.pharmacy_android.ui.app

import android.app.Application
import android.util.Log
import com.khaledamin.pharmacy_android.utils.Constants
import com.khaledamin.pharmacy_android.utils.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule : Application() {

    @Singleton
    @Provides
    fun provideRetrofit(okhttpClient: OkHttpClient, BASE_URL: String): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okhttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(sharedPreferencesManager: SharedPreferencesManager): OkHttpClient {
        val okhttpClient = OkHttpClient.Builder().addInterceptor(Interceptor {
            val request = it.request()
            val url = request.url().toString()
            if (url.contains("addresses") || url.contains("orders") || url.contains("cart") || url.contains("payment") || url.contains("shipping")){
                val newRequest = request.newBuilder().addHeader("Authorization", "Bearer ${sharedPreferencesManager.getBearerToken()}").build()
                it.proceed(newRequest)
            } else {
                it.proceed(request)
            }
        }).build()
        return okhttpClient
    }

    @Provides
    fun provideBaseUrl(): String = Constants.BASE_URL

}