package com.golddog.mask_location.data.api

import com.golddog.mask_location.entity.CoronaList
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

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
    fun getAccumulateData(@Header("APIKey") apiKey: String, @Query("locale") locale: String): Flowable<CoronaList>
}