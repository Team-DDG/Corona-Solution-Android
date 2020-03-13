package com.golddog.mask_location.data.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface MaskApi {
    companion object {
        private const val baseUrl = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1"

        private lateinit var retrofit : Retrofit

        fun createRetrofit(): Retrofit {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }
    }
}