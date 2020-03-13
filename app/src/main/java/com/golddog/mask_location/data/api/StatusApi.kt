package com.golddog.mask_location.data.api

import com.golddog.mask_location.entity.CoronaStatus
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface StatusApi {
    companion object {
        private const val baseUrl = "https://api.dropper.tech/covid19/"

        fun createDrugStoreRetrofit(): StatusApi {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StatusApi::class.java)
        }
    }

    @GET("status/korea")
    fun getAccumulateData(@Header("Authorization") token: String): Single<CoronaStatus>
}