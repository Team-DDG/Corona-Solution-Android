package com.golddog.mask_location.data.api

import com.golddog.mask_location.entity.CoronaList
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface StatusApi {
    companion object {
        private const val baseUrl = "https://api.dropper.tech/covid19/"

        private val interceptor = Interceptor {
            val key = "bc331b7685e857f31988e727b5dee3e495aa45ca8e92d8907ebb270b68a8ec42"
            val request = it.request().newBuilder().addHeader("APIKey", key).build()
            it.proceed(request)
        }

        fun createDrugStoreRetrofit(): StatusApi {
            val okHttpBuilder = OkHttpClient.Builder()
            okHttpBuilder.interceptors().add(interceptor)
            val okHttpClient = okHttpBuilder.build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StatusApi::class.java)
        }
    }

    @GET("status/korea")
    fun getAccumulateData(@Query("locale") locale: String): Single<CoronaList>
}