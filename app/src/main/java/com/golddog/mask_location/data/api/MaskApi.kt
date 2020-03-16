package com.golddog.mask_location.data.api

import com.golddog.mask_location.entity.StoresList
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MaskApi {
    companion object {
        private const val baseUrl = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/"

        fun createMaskRetrofit(): MaskApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MaskApi::class.java)
    }

    @GET("storesByGeo/json")
    fun getAroundMaskData(@Query("lat") lat: Int,
                          @Query("lng") lng: Int,
                          @Query("m") meter: Int): Single<StoresList>
}